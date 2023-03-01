package ru.svoyakmartin.featureCharacter.ui.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureCharacter.domain.model.Character
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepositoryImpl,
) : BaseLoadingErrorViewModel() {
    var charactersCount = 0
        private set

    private val _allCharacters = MutableStateFlow<List<Character>?>(null)
    val allCharacters = _allCharacters.stateFlowWithDelay().filterNotNull()
    private fun setAllCharacters(newCharacters: List<Character>?) {
        _allCharacters.value = newCharacters
    }

    private var query = ""
    fun setQuery(newQuery: String) {
        query = newQuery
        filteredCharacter()
    }

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.charactersCount.collect { response ->
                getDataOrSetError(response)
                    .collect {
                        if (it is Int) charactersCount = it
                    }
            }

            repository.allCharacters
                .flowOn(Dispatchers.IO)
                .conflate()
                .collect {
                    if (query.isBlank()) {
                        setAllCharacters(it)
                    }
                }

            filteredCharacter()
        }
    }

    private fun filteredCharacter() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            repository.filteredCharacter(query)
                .flowOn(Dispatchers.IO)
                .cancellable()
                .collect {
                    setAllCharacters(it)
                }

        }
    }


    fun fetchNextCharactersPartFromWeb() {
        if (isLoading.value) return

        setIsLoading(true)

        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchNextCharactersPartFromWeb()
                .conflate()
                .collect { response ->
                    getDataOrSetError(response).collect {
                        if (it == true) setIsLoading(false)
                    }
                }
        }
    }
}
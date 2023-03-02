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
        if (query != newQuery){
            query = newQuery
            filteredCharacter()
        }
    }

    private lateinit var allCharactersFlow: StateFlow<List<Character>>

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.charactersCount.collect { response ->
                getDataOrSetError(response)
                    .collect {
                        if (it is Int) charactersCount = it
                    }
            }

            allCharactersFlow = repository.allCharacters
                .conflate()
                .stateIn(viewModelScope)
                .stateFlowWithDelay()

            allCharactersFlow.collect {
                if (query.isEmpty()) {
                    setAllCharacters(it)
                }
            }
        }
    }

    private fun filteredCharacter() {
        if (query.isEmpty()) {
            setAllCharacters(allCharactersFlow.value)
        } else {
            searchJob?.cancel()
            setIsLoading(true)
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                repository.filteredCharacter(query)
                    .cancellable()
                    .collect {
                        when (it) {
                            true -> setIsLoading(false)
                            is List<*> -> setAllCharacters(it as List<Character>)
                        }
                    }
            }
        }
    }

    fun fetchNextCharactersPartFromWeb() {
        if (isLoading.value) return

        setIsLoading(true)

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.fetchNextCharactersPartFromWeb()
            getDataOrSetError(response).collect {
                if (it == true) setIsLoading(false)
            }
        }
    }
}
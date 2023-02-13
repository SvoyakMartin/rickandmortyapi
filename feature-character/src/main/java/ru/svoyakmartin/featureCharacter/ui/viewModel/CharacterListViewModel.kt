package ru.svoyakmartin.featureCharacter.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepositoryImpl,
) : ViewModel() {
    val allCharacters = repository.allCharacters
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun fetchNextCharactersPartFromWeb() = viewModelScope.launch {
        repository.fetchNextCharactersPartFromWeb()
    }
}
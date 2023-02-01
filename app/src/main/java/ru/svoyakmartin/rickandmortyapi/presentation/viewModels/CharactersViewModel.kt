package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.presentation.util.stateInStarted5000
import javax.inject.Inject

class CharactersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val allCharacters = repository.allCharacters
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateInStarted5000(viewModelScope, listOf())

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
        .stateInStarted5000(viewModelScope, false)

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun fetchNextCharactersPartFromWeb() = viewModelScope.launch {
        repository.fetchNextCharactersPartFromWeb()
    }
}
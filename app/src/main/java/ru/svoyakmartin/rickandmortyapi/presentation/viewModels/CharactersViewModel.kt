package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import javax.inject.Inject

class CharactersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val allCharacters = repository.allCharacters
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    fun fetchNextCharactersPartFromWeb() = viewModelScope.launch {
        repository.fetchNextCharactersPartFromWeb()
    }
}
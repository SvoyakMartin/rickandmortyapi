package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository

class CharactersViewModel(private val repository: Repository) : ViewModel() {
    val allCharacters = repository.allCharacters

    fun getCharacters() = viewModelScope.launch {
        repository.getCharacters()
    }
}

class CharactersViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
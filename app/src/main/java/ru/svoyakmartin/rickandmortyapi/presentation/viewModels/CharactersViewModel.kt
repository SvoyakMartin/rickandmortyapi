package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.domain.models.Character

class CharactersViewModel(private val repository: Repository) : ViewModel() {
    val allCharacters = repository.allCharacters.asLiveData()

    fun addCharacter(character: Character) = viewModelScope.launch {
        repository.insertCharacter(character)
    }

    fun deleteCharacter(character: Character) = viewModelScope.launch {
        repository.deleteCharacter(character)
    }

    fun deleteAllCharacters() = viewModelScope.launch {
        repository.deleteAllCharacters()
    }

    fun getCharactersSize(): Int {
        return allCharacters.value?.size ?: 0
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
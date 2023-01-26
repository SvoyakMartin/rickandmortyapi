package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository

class CharacterDetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getLocation(id: Int) = repository.getLocation(id)
    fun getEpisodesByCharactersId(id: Int) = repository.getEpisodesByCharactersId(id)
}

class CharacterDetailsViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
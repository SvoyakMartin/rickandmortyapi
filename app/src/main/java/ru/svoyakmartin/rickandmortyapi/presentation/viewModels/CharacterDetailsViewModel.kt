package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import javax.inject.Inject

class CharacterDetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getLocation(id: Int) = repository.getLocation(id)
        .flowOn(Dispatchers.IO)
        .conflate()

    fun getEpisodesByCharactersId(id: Int) = repository.getEpisodesByCharactersId(id)
        .flowOn(Dispatchers.IO)
        .conflate()
}

class CharacterDetailsViewModelFactory @Inject constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
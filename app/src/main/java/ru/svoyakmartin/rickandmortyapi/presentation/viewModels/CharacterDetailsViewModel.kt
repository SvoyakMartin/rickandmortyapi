package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getLocationById(id: Int) = repository.getLocationById(id)
        .flowOn(Dispatchers.IO)
        .conflate()

    fun getEpisodesByCharactersId(id: Int) = repository.getEpisodesByCharacterId(id)
        .flowOn(Dispatchers.IO)
        .conflate()
}
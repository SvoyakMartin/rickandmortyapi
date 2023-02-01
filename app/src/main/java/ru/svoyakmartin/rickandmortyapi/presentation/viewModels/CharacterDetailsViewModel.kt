package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.presentation.util.stateInStarted5000
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _isEpisodesVisible = MutableStateFlow(false)
    val isEpisodesVisible = _isEpisodesVisible
        .stateInStarted5000(viewModelScope, false)

    fun changeEpisodesVisible() {
        _isEpisodesVisible.value = !_isEpisodesVisible.value
    }

    fun getLocationById(id: Int) = repository.getLocationById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateInStarted5000(viewModelScope, null)

    fun getEpisodesByCharacterId(id: Int) = repository.getEpisodesByCharacterId(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateInStarted5000(viewModelScope, listOf())
}
package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.presentation.util.stateInStarted5000
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _isCharactersVisible = MutableStateFlow(false)
    val isCharactersVisible = _isCharactersVisible
        .stateInStarted5000(viewModelScope, false)

    fun changeCharactersVisible() {
        _isCharactersVisible.value = !_isCharactersVisible.value
    }

    fun getCharactersByLocationId(id: Int) = repository.getCharactersByLocationId(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateInStarted5000(viewModelScope, listOf())
}
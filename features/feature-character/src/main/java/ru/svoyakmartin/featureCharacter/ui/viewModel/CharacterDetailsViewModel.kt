package ru.svoyakmartin.featureCharacter.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val repository: CharacterRepositoryImpl) :
    ViewModel() {
    private val _isEpisodesVisible = MutableStateFlow(false)
    val isEpisodesVisible = _isEpisodesVisible
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun changeEpisodesVisible() {
        _isEpisodesVisible.value = !_isEpisodesVisible.value
    }

    suspend fun getCharacterById(id: Int) = repository.getCharacterById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    suspend fun getLocationsMapByIds(locationsIdsList: List<Int>) =
        repository.getLocationsMapByIds(locationsIdsList)
            .flowOn(Dispatchers.IO)
            .conflate()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    suspend fun getEpisodesMapByCharacterId(characterId: Int) =
        repository.getEpisodesMapByCharacterId(characterId)
            .flowOn(Dispatchers.IO)
            .conflate()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
}
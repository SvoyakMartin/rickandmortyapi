package ru.svoyakmartin.featureEpisode.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    private val repository: EpisodeRepositoryImpl,
    private val characterFeatureApi: CharacterFeatureApi
) :
    ViewModel() {
    private val _isCharactersVisible = MutableStateFlow(false)
    val isCharactersVisible = _isCharactersVisible
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun changeCharactersVisible() {
        _isCharactersVisible.value = !_isCharactersVisible.value
    }

    suspend fun getEpisodeById(id: Int) = repository.getEpisodeById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    suspend fun getCharacterMapByEpisodeId(id: Int) = repository.getCharacterMapByEpisodeId(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
}
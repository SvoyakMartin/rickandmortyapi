package ru.svoyakmartin.featureCharacter.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val locationFeatureApi: LocationFeatureApi,
    private val episodeFeatureApi: EpisodeFeatureApi,
    private val flowRouter: FlowRouter
) :
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

    fun navigateToLocation(locationId: Int) {
        flowRouter.navigateTo(locationFeatureApi.getDetailFragment(locationId))
    }

    fun navigateToEpisode(episodeId: Int) {
        flowRouter.navigateTo(episodeFeatureApi.getDetailFragment(episodeId))
    }

    suspend fun getLocationsMapByIds(locationsIdsList: List<Int>) = repository.getLocationsMapByIds(locationsIdsList)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    suspend fun getEpisodesMapByCharacterId(characterId: Int) = repository.getEpisodesMapByCharacterId(characterId)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
}
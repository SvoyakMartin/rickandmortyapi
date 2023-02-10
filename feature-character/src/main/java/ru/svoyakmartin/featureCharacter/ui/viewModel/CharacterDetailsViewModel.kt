package ru.svoyakmartin.featureCharacter.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val locationFeatureApi: LocationFeatureApi,
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

    suspend fun getLocationMapById(locationId: Int) = repository.getLocationMapById(locationId)

//    fun getEpisodesByCharacterId(id: Int) = repository.getEpisodesByCharacterId(id)
//        .flowOn(Dispatchers.IO)
//        .conflate()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
}
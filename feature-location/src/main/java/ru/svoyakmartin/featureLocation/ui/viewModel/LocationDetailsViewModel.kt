package ru.svoyakmartin.featureLocation.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val repository: LocationRepositoryImpl,
    private val characterFeatureApi: CharacterFeatureApi,
    private val flowRouter: FlowRouter
) :
    ViewModel() {
    private val _isCharactersVisible = MutableStateFlow(false)
    val isCharactersVisible = _isCharactersVisible
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun changeCharactersVisible() {
        _isCharactersVisible.value = !_isCharactersVisible.value
    }

    fun getLocationById(id: Int) = repository.getLocationById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    suspend fun getCharacterMapByLocationId(id: Int) = repository.getCharacterMapByLocationId(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    fun navigateToCharacter(characterId: Int) {
        flowRouter.navigateTo(characterFeatureApi.getDetailFragment(characterId))
    }
}
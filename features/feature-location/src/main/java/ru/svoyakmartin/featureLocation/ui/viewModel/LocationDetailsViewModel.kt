package ru.svoyakmartin.featureLocation.ui.viewModel

import android.net.Uri
import android.view.View
import androidx.navigation.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureCore.util.getEntityMapListStateFlow
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import ru.svoyakmartin.featureLocation.data.dataSource.toLocation
import ru.svoyakmartin.featureLocation.data.model.LocationDTO
import ru.svoyakmartin.featureLocation.domain.model.Location
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(private val repository: LocationRepositoryImpl) :
    BaseLoadingErrorViewModel() {
    private val _isCharactersVisible = MutableStateFlow(false)
    val isCharactersVisible = _isCharactersVisible.stateFlowWithDelay()
    fun changeCharactersVisible() {
        _isCharactersVisible.value = !_isCharactersVisible.value
    }

    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.stateFlowWithDelay().filterNotNull()
    private fun setLocation(newLocation: Location) {
        _location.value = newLocation
    }

    suspend fun getLocationById(id: Int) = repository.getLocationById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .collect { response ->
            getDataOrSetError(response).collect {
                when (it) {
                    is Location -> {
                        setLocation(it)
                    }
                    is LocationDTO -> {
                        setLocation(it.toLocation())
                    }
                }
            }
        }

    suspend fun getCharacterMapByLocationId(id: Int) =
        getEntityMapListStateFlow(repository.getCharacterMapByLocationId(id))

    fun navigateToCharacter(view: View, characterId: Int) {
        val uri = Uri.parse("RickAndMortyApi://character/$characterId")
        view.findNavController().navigate(uri)
    }
}
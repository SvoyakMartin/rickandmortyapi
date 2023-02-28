package ru.svoyakmartin.featureLocation.ui.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import ru.svoyakmartin.featureLocation.domain.model.Location
import javax.inject.Inject

class LocationListViewModel @Inject constructor(
    private val repository: LocationRepositoryImpl
) : BaseLoadingErrorViewModel() {
    var locationsCount = 0
        private set

    private val _allLocations = MutableStateFlow<List<Location>?>(null)
    val allLocations = _allLocations.stateFlowWithDelay().filterNotNull()
    private fun setAllLocations(newCharacters: List<Location>) {
        _allLocations.value = newCharacters
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.locationsCount.collect {response ->
                getDataOrSetError(response).collect {
                    if (it is Int) locationsCount = it
                }
            }

            repository.allLocations
                .flowOn(Dispatchers.IO)
                .conflate()
                .collect {
                    setAllLocations(it)
                }
        }
    }

    fun fetchNextLocationsPartFromWeb(){
        if (isLoading.value) return

        setIsLoading(true)

        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchNextLocationsPartFromWeb()
                .conflate()
                .collect { response ->
                    getDataOrSetError(response).collect {
                        if (it == true) setIsLoading(false)
                    }
                }
        }
    }
}
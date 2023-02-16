package ru.svoyakmartin.featureLocation.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import javax.inject.Inject

class LocationListViewModel @Inject constructor(
    private val repository: LocationRepositoryImpl
) : ViewModel() {
    val allLocations = repository.allLocations
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun fetchNextLocationsPartFromWeb() = viewModelScope.launch {
        repository.fetchNextLocationsPartFromWeb()
    }
}
package ru.svoyakmartin.featureLocation.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(private val repository: LocationRepositoryImpl) :
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

//    fun getCharactersByLocationId(id: Int) = repository.getCharactersByLocationId(id)
//        .flowOn(Dispatchers.IO)
//        .conflate()
//        .stateInStarted5000(viewModelScope, listOf())
}
package ru.svoyakmartin.featureLocation.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.conflate
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val repository: LocationRepositoryImpl
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
}
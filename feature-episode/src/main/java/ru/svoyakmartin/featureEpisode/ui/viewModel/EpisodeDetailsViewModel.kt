package ru.svoyakmartin.featureEpisode.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(private val repository: EpisodeRepositoryImpl) :
    ViewModel() {
    private val _isCharactersVisible = MutableStateFlow(false)
//    val isCharactersVisible = _isCharactersVisible
//        .stateInStarted5000(viewModelScope, false)
//
//    fun changeCharactersVisible() {
//        _isCharactersVisible.value = !_isCharactersVisible.value
//    }
//
//    fun getCharactersByEpisodeId(id: Int) = repository.getCharactersByEpisodeId(id)
//        .flowOn(Dispatchers.IO)
//        .conflate()
//        .stateInStarted5000(viewModelScope, listOf())
}
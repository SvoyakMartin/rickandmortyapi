package ru.svoyakmartin.featureEpisode.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(private val repository: EpisodeRepositoryImpl) : ViewModel() {
    val allEpisodes = repository.allEpisodes
        .flowOn(Dispatchers.IO)
        .conflate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun fetchNextEpisodesPartFromWeb() = viewModelScope.launch {
//        repository.fetchNextEpisodesPartFromWeb()
    }
}
package ru.svoyakmartin.featureEpisode.ui.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
    private val repository: EpisodeRepositoryImpl
) : BaseLoadingErrorViewModel() {
    var episodesCount = 0
        private set

    private val _allEpisodes = MutableStateFlow<List<Episode>?>(null)
    val allEpisodes = _allEpisodes.stateFlowWithDelay().filterNotNull()
    private fun setAllEpisodes(newEpisodes: List<Episode>) {
        _allEpisodes.value = newEpisodes
    }

    init {
        viewModelScope.launch {
            repository.episodesCount.collect { response ->
                getDataOrSetError(response).collect {
                    if (it is Int) episodesCount = it
                }
            }

            repository.allEpisodes
                .flowOn(Dispatchers.IO)
                .conflate()
                .collect {
                    setAllEpisodes(it)
                }
        }
    }

    fun fetchNextEpisodesPartFromWeb(){
        if (isLoading.value) return

        setIsLoading(true)

        viewModelScope.launch {
            repository.fetchNextEpisodesPartFromWeb()
                .conflate()
                .collect { response ->
                    getDataOrSetError(response).collect {
                        if (it == true) setIsLoading(false)
                    }
                }
        }
    }
}
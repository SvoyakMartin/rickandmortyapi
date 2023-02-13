package ru.svoyakmartin.featureEpisode.ui.viewModel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import ru.svoyakmartin.featureEpisode.ui.EpisodeClickListener
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeDetailsFragment
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(
    private val repository: EpisodeRepositoryImpl,
    private val flowRouter: FlowRouter
) : ViewModel(),
    EpisodeClickListener {
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
        repository.fetchNextEpisodesPartFromWeb()
    }

    override fun onEpisodeClick(episodeId: Int) {
        flowRouter.navigateTo(EpisodeDetailsFragment.newInstance(episodeId))
    }
}
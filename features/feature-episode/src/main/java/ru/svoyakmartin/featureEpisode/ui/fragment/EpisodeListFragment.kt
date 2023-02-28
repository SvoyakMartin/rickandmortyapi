package ru.svoyakmartin.featureEpisode.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.Lazy
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreUI.*
import ru.svoyakmartin.featureEpisode.databinding.FragmentEpisodesBinding
import ru.svoyakmartin.featureEpisode.ui.EpisodesAdapter
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeFeatureComponentViewModel
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeListViewModel
import javax.inject.Inject

class EpisodeListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: EpisodeListViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentEpisodesBinding
    private val adapter = EpisodesAdapter()

    override fun onAttach(context: Context) {
        EpisodeFeatureComponentDependenciesProvider.featureDependencies =
            findFeatureExternalDependencies()
        viewModel<EpisodeFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initError(viewModel)
        initRecyclerView()
        initRepeatOnLifecycle()
    }

    private fun initRecyclerView() {
        with(binding){
            episodesRecyclerView.init(adapter) {
                if (episodesRecyclerView.isVisibleLastItemWithMaximum(viewModel.episodesCount)) {
                    viewModel.fetchNextEpisodesPartFromWeb()
                }
            }
        }
    }

    private fun initRepeatOnLifecycle() {
        launch {
            viewModel.allEpisodes
                .collect { episodeList ->
                    if (episodeList.isEmpty()) {
                        viewModel.fetchNextEpisodesPartFromWeb()
                    } else {
                        adapter.submitList(episodeList)
                    }
                }
        }

        launch {
            viewModel.isLoading.collect { binding.loadingProgressBar.setVisibility(it) }
        }
    }
}
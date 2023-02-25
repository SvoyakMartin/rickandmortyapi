package ru.svoyakmartin.featureEpisode.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreMvvm.viewModel
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
        initRecyclerView()
        initRepeatOnLifecycle()
    }

    private fun initRecyclerView() {
        with(binding) {
            episodesRecyclerView.adapter = adapter

            val scrollListener = object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val adapterItemCount = adapter.itemCount

                    if (adapterItemCount < viewModel.episodesCount) {
                        val lastVisibleItemPosition =
                            (episodesRecyclerView.layoutManager as LinearLayoutManager)
                                .findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == adapter.itemCount - 1
                        ) {
                            loadNextPart()
                        }
                    }
                }
            }
            episodesRecyclerView.addOnScrollListener(scrollListener)
        }
    }

    private fun initRepeatOnLifecycle() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.allEpisodes.collect {
                        if (it.isNullOrEmpty()) {
                            loadNextPart()
                        } else {
                            adapter.submitList(it)
                            viewModel.setIsLoading(false)
                        }
                    }
                }

                launch {
                    viewModel.isLoading.collect { showHideLoadingView(it) }
                }
            }
        }
    }

    private fun showHideLoadingView(visibility: Boolean) {
        binding.loadingProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun loadNextPart() {
        with(viewModel) {
            if (!isLoading.value) {
                apply {
                    setIsLoading(true)
                    fetchNextEpisodesPartFromWeb()
                }
            }
        }
    }
}
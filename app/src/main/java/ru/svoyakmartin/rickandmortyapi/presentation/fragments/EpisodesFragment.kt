package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentEpisodesBinding
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.EpisodeClickListener
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.EpisodesAdapter
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.EpisodesViewModel
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import javax.inject.Inject

class EpisodesFragment : Fragment(), EpisodeClickListener {
    private lateinit var binding: FragmentEpisodesBinding

    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: EpisodesViewModel by viewModels { viewModelFactory.get() }
    private val adapter = EpisodesAdapter(this)

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
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

            episodesRecyclerView.adapter = adapter
            episodesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((episodesRecyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition() == adapter.itemCount - 1
                    ) {
                        loadNextPart()
                    }
                }
            })
        }
    }

    private fun showHideLoadingView(visibility: Boolean) {
        binding.loadingProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun loadNextPart() {
        with(viewModel){
            if (!isLoading.value) {
                apply {
                    setIsLoading(true)
                    fetchNextEpisodesPartFromWeb()
                }
            }
        }
    }

    override fun onEpisodeClick(episode: Episode) {
//        requireActivity().supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            addToBackStack(DEFAULT_BACK_STACK)
//
//            replace(
//                R.id.base_fragment_container,
//                EpisodeDetailsFragment::class.java,
//                bundleOf(EPISODES_FIELD to episode)
//            )
//        }
    }
}
package ru.svoyakmartin.featureEpisode.ui.fragment

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
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.featureEpisode.databinding.FragmentEpisodesBinding
import ru.svoyakmartin.featureEpisode.ui.EpisodeClickListener
import ru.svoyakmartin.featureEpisode.ui.EpisodesAdapter
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeListViewModel
import javax.inject.Inject

class EpisodeListFragment : Fragment(), EpisodeClickListener {
    private lateinit var binding: FragmentEpisodesBinding

//    @Inject
//    lateinit var viewModelFactory: Lazy<ViewModelFactory>
//    private val viewModel: EpisodeListViewModel by viewModels { viewModelFactory.get() }
//    private val adapter = EpisodesAdapter(this)

    override fun onAttach(context: Context) {
//        (requireActivity().application as App).appComponent.inject(this)

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
//                    launch {
//                        viewModel.allEpisodes.collect {
//                            if (it.isNullOrEmpty()) {
//                                loadNextPart()
//                            } else {
//                                adapter.submitList(it)
//                                viewModel.setIsLoading(false)
//                            }
//                        }
//                    }

//                    launch {
//                        viewModel.isLoading.collect { showHideLoadingView(it) }
//                    }
                }
            }

//            episodesRecyclerView.adapter = adapter
//            episodesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if ((episodesRecyclerView.layoutManager as LinearLayoutManager)
//                            .findLastVisibleItemPosition() == adapter.itemCount - 1
//                    ) {
//                        loadNextPart()
//                    }
//                }
//            })
        }
    }

    private fun showHideLoadingView(visibility: Boolean) {
        binding.loadingProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

//    private fun loadNextPart() {
//        with(viewModel){
//            if (!isLoading.value) {
//                apply {
//                    setIsLoading(true)
//                    fetchNextEpisodesPartFromWeb()
//                }
//            }
//        }
//    }

    override fun onEpisodeClick(episode: ru.svoyakmartin.featureEpisode.domain.model.Episode) {
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
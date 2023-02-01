package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.*
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentLocationsBinding
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.LocationClickListener
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.LocationsAdapter
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.LocationsViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.ViewModelFactory
import javax.inject.Inject

class LocationsFragment : Fragment(), LocationClickListener {
    private lateinit var binding: FragmentLocationsBinding
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: LocationsViewModel by viewModels { viewModelFactory.get() }
    private val adapter = LocationsAdapter(this)

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)

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
                        viewModel.allLocations.collect {
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

            locationsRecyclerView.adapter = adapter
            locationsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((locationsRecyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition() == adapter.itemCount - 1
                    ) {
                        loadNextPart()
                    }
                }
            })
        }
    }

    private fun loadNextPart() {
        with(viewModel){
            if (!isLoading.value) {
                apply {
                    setIsLoading(true)
                    fetchNextLocationsPartFromWeb()
                }
            }
        }
    }

    private fun showHideLoadingView(visibility: Boolean) {
        binding.loadingProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun onLocationClick(location: Location) {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(DEFAULT_BACK_STACK)

            replace(
                R.id.base_fragment_container,
                LocationDetailsFragment::class.java,
                bundleOf(LOCATIONS_FIELD to location)
            )
        }
    }
}
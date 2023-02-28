package ru.svoyakmartin.featureLocation.ui.fragment

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
import androidx.recyclerview.widget.RecyclerView
import dagger.Lazy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreUI.*
import ru.svoyakmartin.featureLocation.databinding.FragmentLocationsBinding
import ru.svoyakmartin.featureLocation.ui.LocationsAdapter
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentViewModel
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationListViewModel
import javax.inject.Inject

class LocationListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: LocationListViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentLocationsBinding
    private val adapter = LocationsAdapter()

    override fun onAttach(context: Context) {
        LocationFeatureComponentDependenciesProvider.featureDependencies =
            findFeatureExternalDependencies()
        viewModel<LocationFeatureComponentViewModel>().component.inject(this)

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
        initError(viewModel)
        initRecyclerView()
        initRepeatOnLifecycle()
    }

    private fun initRecyclerView() {
        with(binding){
            locationsRecyclerView.init(adapter) {
                if (locationsRecyclerView.isVisibleLastItemWithMaximum(viewModel.locationsCount)) {
                    viewModel.fetchNextLocationsPartFromWeb()
                }
            }
        }
    }

    private fun initRepeatOnLifecycle() {
        launch {
            viewModel.allLocations
                .collect { locationList ->
                    if (locationList.isEmpty()) {
                        viewModel.fetchNextLocationsPartFromWeb()
                    } else {
                        adapter.submitList(locationList)
                    }
                }
        }

        launch {
            viewModel.isLoading.collect { binding.loadingProgressBar.setVisibility(it) }
        }
    }
}
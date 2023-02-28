package ru.svoyakmartin.featureLocation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.Lazy
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreUI.initError
import ru.svoyakmartin.coreUI.launch
import ru.svoyakmartin.coreUI.setVisibility
import ru.svoyakmartin.coreUI.viewModel
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import ru.svoyakmartin.featureLocation.LOCATIONS_FIELD
import ru.svoyakmartin.featureLocation.R
import ru.svoyakmartin.featureLocation.databinding.FragmentLocationDetailsBinding
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationDetailsViewModel
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentViewModel
import ru.svoyakmartin.featureTheme.R as themeR
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: LocationDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentLocationDetailsBinding

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
        binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initError(viewModel)
        initLocation()
    }

    private fun initLocation() {
        val locationId = arguments?.getInt(LOCATIONS_FIELD)

        locationId?.let {
            launch {
                viewModel.location.collect { setLocation(it) }
            }
            launch {
                viewModel.getLocationById(locationId)
            }
        }
    }

    private fun setLocation(location: Location) {
        with(location) {
            binding.apply {
                locationName.text = name
                locationType.text = type
                locationDimension.text = dimension
            }

            initCharacters(id)
        }
    }

    private fun initCharacters(locationId: Int) {
        launch {
            viewModel.getCharacterMapByLocationId(locationId).collect { setCharactersView(it) }
        }
        launch {
            viewModel.isCharactersVisible.collect { showHideCharacters(it) }
        }
    }

    private fun setCharactersView(charactersMap: List<EntityMap>) {
        val size = charactersMap.size

        with(binding) {
            locationCharacters.text = getString(R.string.location_characters_header_text, size)

            if (size > 0) {
                showHideCharacters.apply {
                    setVisibility(true)
                    setOnClickListener {
                        viewModel.changeCharactersVisible()
                    }
                }

                charactersMap.forEach { entityMap ->
                    val textView = TextView(context).apply {
                        text = entityMap.name

                        setOnClickListener {
                            viewModel.navigateToCharacter(it, entityMap.id)
                        }
                    }

                    charactersContainer.addView(textView)
                }
            }
        }
    }

    private fun showHideCharacters(isCharactersVisible: Boolean) {
        with(binding) {
            charactersContainer.visibility =
                if (isCharactersVisible) View.VISIBLE else View.GONE
            charactersScroll.visibility = charactersContainer.visibility

            showHideCharacters.apply {
                setTextColor(
                    context.getColor(
                        if (isCharactersVisible)
                            themeR.color.dark_red
                        else
                            themeR.color.dark_green
                    )
                )
                text = getString(
                    if (isCharactersVisible)
                        R.string.characters_hide
                    else
                        R.string.characters_show
                )
            }
        }
    }

    companion object {
        fun newInstance(locationId: Int): LocationDetailsFragment =
            LocationDetailsFragment().apply {
                arguments = bundleOf(LOCATIONS_FIELD to locationId)
            }
    }
}
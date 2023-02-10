package ru.svoyakmartin.featureLocation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.featureLocation.LOCATIONS_FIELD
import ru.svoyakmartin.featureLocation.R
import ru.svoyakmartin.featureLocation.databinding.FragmentLocationDetailsBinding
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationDetailsViewModel
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentViewModel
import ru.svoyakmartin.featureTheme.R as themeR
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: LocationDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentLocationDetailsBinding

    override fun onAttach(context: Context) {
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

        val locationId = arguments?.getInt(LOCATIONS_FIELD)

        locationId?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getLocationById(locationId).collect { location ->
                            location?.let { initViews(it) }
                        }
                    }
                }
            }
        }
    }

    private fun initViews(location: Location) {
        binding.apply {
            with(location) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        launch {
//                            viewModel.getCharactersByLocationId(id)
//                                .collect { setCharactersView(it) }
//                        }

                        launch {
                            viewModel.isCharactersVisible
                                .collect { showHideCharacters(it) }
                        }
                    }
                }

                locationName.text = name
                locationType.text = type
                locationDimension.text = dimension
            }
        }
    }

//    private fun setCharactersView(charactersList: List<ru.svoyakmartin.featureCharacter.domain.model.Character>?) {
//        val size = charactersList?.size ?: 0
//
//        with(binding) {
//            locationCharacters.text = getString(R.string.location_characters_header_text, size)
//
//            if (size > 0) {
//                showHideCharacters.apply {
//                    visibility = View.VISIBLE
//
//                    setOnClickListener {
//                        viewModel.changeCharactersVisible()
//                    }
//                }
//
//                charactersList?.forEach { character ->
//                    val textView = TextView(context).apply {
//                        text = character.name
//
//                        setOnClickListener {
//                            goToCharacter(character)
//                        }
//                    }
//
//                    charactersContainer.addView(textView)
//                }
//            }
//        }
//    }

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
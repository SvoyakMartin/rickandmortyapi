package ru.svoyakmartin.featureCharacter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.Lazy
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreUI.*
import ru.svoyakmartin.featureCharacter.CHARACTERS_FIELD
import ru.svoyakmartin.featureCharacter.R
import ru.svoyakmartin.featureCharacter.databinding.FragmentCharacterDetailsBinding
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterDetailsViewModel
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentViewModel
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import ru.svoyakmartin.featureTheme.R as themeR
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: CharacterDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onAttach(context: Context) {
        CharacterFeatureComponentDependenciesProvider.featureDependencies =
            findFeatureExternalDependencies()
        viewModel<CharacterFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initCharacter() {
        val characterId = arguments?.getInt(CHARACTERS_FIELD)

        characterId?.let {
            launch {
                viewModel.character
                    .collect {
                        setCharacter(it)
                    }
            }
            launch {
                viewModel.getCharacterById(characterId)
            }
        }
    }

    private fun setCharacter(character: Character) {
        with(character) {
            binding.apply {
                characterName.text = name
                val speciesAndType = "$species ${if (type.isNotEmpty()) ": $type" else ""}"
                characterSpecies.text = speciesAndType
                characterGender.text = gender.name
                lastSeen.setAliveStatus(status.name)
            }

            initImageView(image)
            initLocations(location, origin)
            initEpisodes(id)
        }
    }

    private fun initImageView(imageURL: String) {
        val imageView = binding.characterImage

        Glide.with(imageView.context)
            .load(imageURL)
            .into(imageView)

        //TODO try catch
    }

    private fun initEpisodes(characterId: Int) {
        launch {
            viewModel.getEpisodesMapByCharacterId(characterId).collect { setEpisodesView(it) }
        }
        launch {
            viewModel.isEpisodesVisible.collect { showHideEpisodes(it) }
        }
    }

    private fun initLocations(location: Int?, origin: Int?) {
        val locationsIdsList = mutableSetOf<Int>()

        location?.let { locationsIdsList.add(it) }

        if (origin == null) {
            setOrigin(origin)
        } else {
            locationsIdsList.add(origin)
        }

        if (locationsIdsList.isNotEmpty()) {
            launch {
                viewModel.getLocationsMapByIds(locationsIdsList)
                    .collect { setLocations(location, origin, it) }
            }
        }
    }

    private fun initViews() {
        initError(viewModel)
        initCharacter()
    }

    private fun setLocations(lastSeenId: Int?, originId: Int?, locationsMapList: List<EntityMap>) {
        locationsMapList.forEach {
            when (it.id) {
                lastSeenId -> setLastSeen(it)
                originId -> setOrigin(it)
            }
        }
    }

    private fun setLastSeen(locationMap: EntityMap) {
        binding.lastSeen.apply {
            setOnClickListener {
                if (isFinded()) {
                    viewModel.navigateToLocation(it, locationMap.id)
                } else {
                    onClick()
                }
            }

            setLocation(locationMap.name)
        }
    }

    private fun setOrigin(locationMap: EntityMap?) {
        binding.characterOriginLocation.apply {
            locationMap?.let {
                setOnClickListener {
                    viewModel.navigateToLocation(it, locationMap.id)
                }
            }

            text = getString(
                R.string.origin_location_text,
                locationMap?.name ?: getString(R.string.origin_location_unknown)
            )
        }
    }

    private fun setEpisodesView(episodesMapList: List<EntityMap>) {
        val size = episodesMapList.size

        with(binding) {
            characterEpisodes.text = getString(R.string.episodes_header_text, size)

            if (size > 0) {
                showHideEpisodes.apply {
                    setVisibility(true)
                    setOnClickListener {
                        viewModel.changeEpisodesVisible()
                    }
                }

                episodesMapList.forEach { entityMap ->
                    val textView = TextView(context).apply {
                        text = entityMap.name

                        setOnClickListener {
                            viewModel.navigateToEpisode(it, entityMap.id)
                        }
                    }

                    episodesContainer.addView(textView)
                }
            }
        }
    }

    private fun showHideEpisodes(isEpisodesVisible: Boolean) {
        with(binding) {
            episodesContainer.visibility =
                if (isEpisodesVisible) View.VISIBLE else View.GONE
            episodesScroll.visibility = episodesContainer.visibility

            showHideEpisodes.apply {
                setTextColor(
                    context.getColor(
                        if (isEpisodesVisible)
                            themeR.color.dark_red
                        else
                            themeR.color.dark_green
                    )
                )
                text = getString(
                    if (isEpisodesVisible)
                        R.string.episodes_hide
                    else
                        R.string.episodes_show
                )
            }
        }
    }

    companion object {
        fun newInstance(characterId: Int): CharacterDetailsFragment =
            CharacterDetailsFragment().apply {
                arguments = bundleOf(CHARACTERS_FIELD to characterId)
            }
    }
}
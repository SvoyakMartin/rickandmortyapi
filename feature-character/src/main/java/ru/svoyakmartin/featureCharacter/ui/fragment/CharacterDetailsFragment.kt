package ru.svoyakmartin.featureCharacter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreFlow.ui.FlowFragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.featureCharacter.CHARACTERS_FIELD
import ru.svoyakmartin.featureCharacter.R
import ru.svoyakmartin.featureCharacter.databinding.FragmentCharacterDetailsBinding
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCharacter.ui.customView.LastSeenAnimView
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterDetailsViewModel
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentViewModel
import ru.svoyakmartin.featureTheme.R as themeR
import javax.inject.Inject

class CharacterDetailsFragment : FlowFragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: CharacterDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onAttach(context: Context) {
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

        val characterId = arguments?.getInt(CHARACTERS_FIELD)
        characterId?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getCharacterById(characterId).collect { character ->
                            character?.let { initViews(it) }
                        }
                    }
                }
            }
        }
    }

    private fun initViews(character: Character) {
        binding.apply {
            with(character) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        launch {
//                            viewModel.getEpisodesByCharacterId(id)
//                                .collect { setEpisodesView(it) }
//                        }
//
                        location?.let {
                            launch {
                                viewModel.getLocationMapById(it)
                                    .collect { setLastSeen(it) }
                            }
                        }

                        if (origin == null) {
                            setOrigin(origin)
                        } else {
                            launch {
                                viewModel.getLocationMapById(origin)
                                    .collect {setOrigin(it) }
                            }
                        }

                        launch {
                            viewModel.isEpisodesVisible
                                .collect { showHideEpisodes(it) }
                        }
                    }
                }

                Glide.with(characterImage.context)
                    .load(image)
                    .into(characterImage)

                characterName.text = name
                val speciesAndType = "$species ${if (type.isNotEmpty()) ": $type" else ""}"
                characterSpecies.text = speciesAndType
                characterGender.text = gender.name
                lastSeen.setAliveStatus(LastSeenAnimView.AliveStatus.valueOf(status.name))
            }
        }
    }

    private fun setLastSeen(locationMap: Map<String, Int>?) {
        locationMap?.let {
            binding.lastSeen.apply {
                it.entries.forEach { entry ->
                    setOnClickListener {
                        if (isFinded()) {
                            viewModel.navigateToLocation(entry.value)
                        } else {
                            onClick()
                        }
                    }

                    setLocation(entry.key)
                }

            }
        }
    }

    private fun setOrigin(locationMap: Map<String, Int>?) {
        binding.characterOriginLocation.apply {
            var locationName:String? = null
            locationMap?.let {
                it.entries.forEach {entry ->
                    locationName = entry.key

                    setOnClickListener {
                        viewModel.navigateToLocation(entry.value)
                    }
                }
            }

            text = getString(
                R.string.origin_location_text,
                locationName?: getString(R.string.origin_location_unknown)
            )
        }
    }

//    private fun setEpisodesView(episodesList: List<Episode>?) {
//        val size = episodesList?.size ?: 0
//
//        with(binding) {
//            characterEpisodes.text = getString(R.string.episodes_header_text, size)
//
//            if (size > 0) {
//                showHideEpisodes.apply {
//                    visibility = View.VISIBLE
//
//                    setOnClickListener {
//                        viewModel.changeEpisodesVisible()
//                    }
//                }
//
//                episodesList?.forEach { episode ->
//                    val textView = TextView(context).apply {
//                        val episodeName = "${episode.episode} - ${episode.name}"
//
//                        text = episodeName
//
//                        setOnClickListener {
//                            goToEpisode(episode)
//                        }
//                    }
//
//                    episodesContainer.addView(textView)
//                }
//            }
//        }
//    }

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
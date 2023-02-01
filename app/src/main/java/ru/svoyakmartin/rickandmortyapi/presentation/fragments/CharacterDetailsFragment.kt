package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.*
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharacterDetailsBinding
import ru.svoyakmartin.rickandmortyapi.presentation.customView.LastSeenAnimView.AliveStatus
import ru.svoyakmartin.rickandmortyapi.presentation.util.serializable
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharacterDetailsViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.ViewModelFactory
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: CharacterDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
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

        val character = arguments?.serializable(CHARACTERS_FIELD, Character::class.java)!!

        binding.apply {
            with(character) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            viewModel.getEpisodesByCharacterId(id)
                                .collect { setEpisodesView(it) }
                        }

                        location?.let {
                            launch {
                                viewModel.getLocationById(it)
                                    .collect { setLastSeen(it) }
                            }
                        }

                        if (origin == null) {
                            setOrigin(origin)
                        } else {
                            launch {
                                viewModel.getLocationById(origin)
                                    .collect { setOrigin(it) }
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
                lastSeen.setAliveStatus(AliveStatus.valueOf(status.name))
            }
        }
    }

    private fun setLastSeen(location: Location?) {
        location?.let {
            binding.lastSeen.apply {
                setOnClickListener {
                    if (isFinded()) {
                        goToLocation(location)
                    } else {
                        onClick()
                    }
                }

                setLocation(location.name)
            }
        }
    }

    private fun setOrigin(location: Location?) {
        binding.characterOriginLocation.apply {
            text = getString(
                R.string.origin_location_text,
                location?.name ?: getString(R.string.origin_location_unknown)
            )
            location?.let {
                setOnClickListener {
                    goToLocation(location)
                }
            }
        }
    }

    private fun setEpisodesView(episodesList: List<Episode>?) {
        val size = episodesList?.size ?: 0

        with(binding) {
            characterEpisodes.text = getString(R.string.episodes_header_text, size)

            if (size > 0) {
                showHideEpisodes.apply {
                    visibility = View.VISIBLE

                    setOnClickListener {
                        viewModel.changeEpisodesVisible()
                    }
                }

                episodesList?.forEach { episode ->
                    val textView = TextView(context).apply {
                        val episodeName = "${episode.episode} - ${episode.name}"

                        text = episodeName

                        setOnClickListener {
                            goToEpisode(episode)
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
                            R.color.dark_red
                        else
                            R.color.dark_green
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

    private fun goToEpisode(episode: Episode) {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(DEFAULT_BACK_STACK)

            replace(
                R.id.base_fragment_container,
                EpisodeDetailsFragment::class.java,
                bundleOf(EPISODES_FIELD to episode)
            )
        }
    }

    private fun goToLocation(location: Location) {
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
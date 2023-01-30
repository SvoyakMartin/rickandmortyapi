package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.R
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

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    private lateinit var binding: FragmentCharacterDetailsBinding
    private var originLocation: Location? = null
    private var lastSeenLocation: Location? = null
    private var episodes: List<Episode>? = null
    private var isEpisodesVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = arguments?.serializable("character", Character::class.java)!!

        binding.apply {
            with(character) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            viewModel.getEpisodesByCharactersId(id)
                                .collect {
                                    episodes = it
                                    showEpisodes()
                                }
                        }

                        location?.let {
                            launch {
                                viewModel.getLocationById(it)
                                    .collect {
                                        lastSeenLocation = it
                                        setLastSeen()
                                    }
                            }
                        }

                        if (origin == null) {
                            setOrigin()
                        } else {
                            launch {
                                viewModel.getLocationById(origin)
                                    .collect {
                                        originLocation = it
                                        setOrigin()
                                    }
                            }
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

    private fun setLastSeen() {
        lastSeenLocation?.let {
            binding.lastSeen.apply {
                setOnClickListener {
                    if (isFind()) {
                        // TODO: goto location
                    }
                }
                setLocation(it.name)
            }
        }
    }

    private fun setOrigin() {
        binding.characterOriginLocation.apply {
            text = getString(
                R.string.origin_location_text,
                originLocation?.name ?: getString(R.string.origin_location_unknown)
            )
            setOnClickListener {
                // TODO: goto location

            }
        }
    }

    private fun showEpisodes() {
        val size = episodes?.size ?: 0
        with(binding) {
            characterEpisodes.text = getString(R.string.episodes_header_text, size)

            if (size > 0) {
                showHideEpisodes.apply {
                    visibility = View.VISIBLE

                    setOnClickListener {
                        isEpisodesVisible = !isEpisodesVisible

                        episodesContainer.visibility =
                            if (isEpisodesVisible) View.VISIBLE else View.GONE
                        // для скрытия пустого скролла
                        episodesScroll.visibility = episodesContainer.visibility

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

                episodes?.forEach { episode ->
                    val textView = TextView(context).apply {
                        val episodeName = "${episode.episode} - ${episode.name}"

                        text = episodeName

                        setOnClickListener {
                            // TODO: go to episode
                        }
                    }

                    episodesContainer.addView(textView)
                }
            }
        }
    }
}
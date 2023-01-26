package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
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
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharacterDetailsViewModelFactory

class CharacterDetailsFragment : Fragment() {
    private val viewModel: CharacterDetailsViewModel by viewModels {
        CharacterDetailsViewModelFactory((requireActivity().application as App).repository)
    }
    private lateinit var binding: FragmentCharacterDetailsBinding
    private var originLocation: Location? = null
    private var lastSeenLocation: Location? = null
    private var episodes: List<Episode>? = null

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
                lifecycleScope.launch {
                    viewModel.getEpisodesByCharactersId(id).flowWithLifecycle(lifecycle)
                        .collect {
                            episodes = it
                            showEpisodes()
                        }
                }

                location?.let {
                    lifecycleScope.launch {
                        viewModel.getLocation(it).flowWithLifecycle(lifecycle)
                            .collect {
                                lastSeenLocation = it
                                setLastSeen()
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

                if (origin == null) {
                    setOrigin()
                } else {
                    lifecycleScope.launch {
                        viewModel.getLocation(origin).flowWithLifecycle(lifecycle)
                            .collect {
                                originLocation = it
                                setOrigin()
                            }
                    }
                }
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
        // TODO:
    }
}
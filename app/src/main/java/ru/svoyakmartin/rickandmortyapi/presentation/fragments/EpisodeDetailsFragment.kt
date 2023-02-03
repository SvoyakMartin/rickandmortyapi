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
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.*
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentEpisodeDetailsBinding
import ru.svoyakmartin.rickandmortyapi.presentation.util.serializable
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.EpisodeDetailsViewModel
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: EpisodeDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentEpisodeDetailsBinding

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val episode = arguments?.serializable(EPISODES_FIELD, Episode::class.java)!!

        binding.apply {
            with(episode) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            viewModel.getCharactersByEpisodeId(id)
                                .collect { setCharactersView(it) }
                        }

                        launch {
                            viewModel.isCharactersVisible
                                .collect { showHideCharacters(it) }
                        }
                    }
                }

                episodeName.text = name
                episodeCode.text = this.episode
                episodeData.text = air_date
            }
        }
    }

    private fun setCharactersView(charactersList: List<Character>?) {
        val size = charactersList?.size ?: 0

        with(binding) {
            episodeCharacters.text = getString(R.string.episode_characters_header_text, size)

            if (size > 0) {
                showHideCharacters.apply {
                    visibility = View.VISIBLE

                    setOnClickListener {
                        viewModel.changeCharactersVisible()
                    }
                }

                charactersList?.forEach { character ->
                    val textView = TextView(context).apply {
                        text = character.name

                        setOnClickListener {
                            goToCharacter(character)
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
                            R.color.dark_red
                        else
                            R.color.dark_green
                    )
                )
                text = getString(
                    if (isCharactersVisible)
                        R.string.episodes_hide
                    else
                        R.string.episodes_show
                )
            }
        }
    }

    private fun goToCharacter(character: Character) {
//        requireActivity().supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            addToBackStack(DEFAULT_BACK_STACK)
//
//            replace(
//                R.id.base_fragment_container,
//                CharacterDetailsFragment::class.java,
//                bundleOf(CHARACTERS_FIELD to character)
//            )
//        }
    }
}
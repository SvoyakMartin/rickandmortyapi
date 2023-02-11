package ru.svoyakmartin.featureEpisode.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.featureTheme.R as themeR
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.featureEpisode.EPISODES_FIELD
import ru.svoyakmartin.featureEpisode.R
import ru.svoyakmartin.featureEpisode.databinding.FragmentEpisodeDetailsBinding
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeDetailsViewModel
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeFeatureComponentViewModel
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: EpisodeDetailsViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentEpisodeDetailsBinding

    override fun onAttach(context: Context) {
        viewModel<EpisodeFeatureComponentViewModel>().component.inject(this)
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

        val episodeId = arguments?.getInt(EPISODES_FIELD)

        episodeId?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getEpisodeById(episodeId).collect { episode ->
                            episode?.let { initViews(it) }
                        }
                    }
                }
            }
        }
    }

    private fun initViews(episode: Episode){
        binding.apply {
            with(episode) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            viewModel.getCharacterMapByEpisodeId(id)
                                .collect {setCharactersView(it) }
                        }

                        launch {
                            viewModel.isCharactersVisible
                                .collect { showHideCharacters(it) }
                        }
                    }
                }

                episodeName.text = name
                episodeCode.text = this.episode
                episodeData.text = airDate
            }
        }
    }

    private fun setCharactersView(charactersMap: Map<String, Int>?) {
        val size = charactersMap?.size ?: 0

        with(binding) {
            episodeCharacters.text = getString(R.string.episode_characters_header_text, size)

            if (size > 0) {
                showHideCharacters.apply {
                    visibility = View.VISIBLE

                    setOnClickListener {
                        viewModel.changeCharactersVisible()
                    }
                }

                charactersMap?.forEach { entry ->
                    val textView = TextView(context).apply {
                        text = entry.key

                        setOnClickListener {
                            viewModel.navigateToCharacter(entry.value)
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
        fun newInstance(locationId: Int): EpisodeDetailsFragment =
            EpisodeDetailsFragment().apply {
                arguments = bundleOf(EPISODES_FIELD to locationId)
            }
    }
}
package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.CHARACTERS_FIELD
import ru.svoyakmartin.rickandmortyapi.DEFAULT_BACK_STACK
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharactersBinding
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.CharactersAdapter
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.CharacterClickListener
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharactersViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.ViewModelFactory
import javax.inject.Inject


class CharactersFragment : Fragment(), CharacterClickListener {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: CharactersViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentCharactersBinding
    private val adapter = CharactersAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.allCharacters.collect {
                            if (it.isNullOrEmpty()) {
                                loadNextPart()
                            } else {
                                adapter.submitList(it)
                                viewModel.setIsLoading(false)
                            }
                        }
                    }

                    launch {
                        viewModel.isLoading.collect { showHideLoadingView(it) }
                    }
                }
            }

            charactersRecyclerView.adapter = adapter
            charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((charactersRecyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition() == adapter.itemCount - 1
                    ) {
                        loadNextPart()
                    }
                }
            })
        }
    }

    private fun showHideLoadingView(visibility: Boolean) {
        binding.loadingProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun loadNextPart() {
        with(viewModel){
            if (!isLoading.value) {
                apply {
                    setIsLoading(true)
                    fetchNextCharactersPartFromWeb()
                }
            }
        }
    }

    override fun onCharacterClick(character: Character) {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(DEFAULT_BACK_STACK)

            replace(
                R.id.base_fragment_container,
                CharacterDetailsFragment::class.java,
                bundleOf(CHARACTERS_FIELD to character)
            )
        }
    }
}
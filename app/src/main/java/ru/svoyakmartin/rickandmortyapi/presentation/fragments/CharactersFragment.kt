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
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharactersBinding
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.CharactersAdapter
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.CharactersClickListener
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharactersViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharactersViewModelFactory
import javax.inject.Inject


class CharactersFragment : Fragment(), CharactersClickListener {
    @Inject
    lateinit var viewModelFactory: CharactersViewModelFactory
    private val viewModel: CharactersViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentCharactersBinding
    private val adapter = CharactersAdapter(this)
    private var isLoading = false

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

        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.allCharacters.collect {
                        if (it.isNullOrEmpty()) {
                            loadNextPart()
                        } else {
                            submitList(it)
                            isLoading = false
                            loadingProgressBar.visibility = View.GONE
                        }
                    }
                }
            }

            charactersRecyclerView.adapter = adapter
            charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((charactersRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == adapter.itemCount - 1) {
                        loadNextPart()
                    }
                }
            })
        }
    }

    private fun loadNextPart() {
        if (!isLoading) {
            isLoading = true
            binding.loadingProgressBar.visibility = View.VISIBLE
            viewModel.getCharacters()
        }
    }

    private fun submitList(list: List<Character>) {
        adapter.submitList(list)
    }

    override fun onCharacterClick(character: Character) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("UserStack")
            replace(
                R.id.fragmentContainerView,
                CharacterDetailsFragment::class.java,
                bundleOf("character" to character)
            )
        }
    }
}
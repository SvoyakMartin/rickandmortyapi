package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharacterBinding
import ru.svoyakmartin.rickandmortyapi.domain.models.Character
import ru.svoyakmartin.rickandmortyapi.screens.main.characters.CharactersAdapter
import ru.svoyakmartin.rickandmortyapi.screens.main.characters.CharactersClickListener
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharactersViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharactersViewModelFactory


class CharactersFragment : Fragment(), CharactersClickListener {
    private val viewModel: CharactersViewModel by viewModels {
        CharactersViewModelFactory((requireActivity().application as App).repository)
    }
    private lateinit var binding: FragmentCharacterBinding
    private val adapter = CharactersAdapter(this)
    private var isDelete = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.allCharacters.observe(viewLifecycleOwner) {
                submitList(it)
            }

            charactersRecyclerView.adapter = adapter
            charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((charactersRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == adapter.itemCount - 1) {
                        var size = viewModel.getCharactersSize()
                        for (i in 1..3) {
                            val character =
                                Character(++size, "demo Scroll", "", "", "", "", "", "", "")
                            viewModel.addCharacter(character)
                        }
                    }
                }
            })

            settingsButton.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("UserStack")
                    .replace(R.id.fragmentContainerView, SettingsFragment())
                    .commit()
            }

            addButton.setOnClickListener {
                var size = viewModel.getCharactersSize()
                val character = Character(++size, "demo Added", "", "", "", "", "", "", "")
                viewModel.addCharacter(character)
            }

            deleteAllButton.setOnClickListener {
                viewModel.deleteAllCharacters()
            }

            deleteButton.setOnCheckedChangeListener { _, check ->
                isDelete = check
            }
        }
    }

    private fun submitList(list: List<Character>) {
        adapter.submitList(list)
    }

    override fun onCharacterClick(character: Character) {
        if (isDelete) {
            viewModel.deleteCharacter(character)
        } else {
            parentFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("UserStack")
                .replace(R.id.fragmentContainerView, LocationsFragment())
                .commit()
        }
    }
}
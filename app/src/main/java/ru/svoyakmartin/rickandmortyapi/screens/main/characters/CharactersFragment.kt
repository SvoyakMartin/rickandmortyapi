package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharacterBinding
import ru.svoyakmartin.rickandmortyapi.models.Character
import ru.svoyakmartin.rickandmortyapi.screens.main.locations.LocationsFragment


class CharactersFragment : Fragment(), CharactersClickListener {
    private lateinit var binding: FragmentCharacterBinding
    private val adapter = CharactersAdapter(this)

    private fun getExampleDataList(): MutableList<Character> {
        return mutableListOf(
            Character(0, "Keara", "", "", "", "", "", "", "", listOf("The ABC's of Beth"), "", ""),
            Character(
                1,
                "Sleepy Gary",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                listOf("Total Rickall"),
                "",
                ""
            ),
            Character(2, "Pussifer", "", "", "", "", "", "", "", listOf("Rickmurai Jack"), "", ""),
            Character(3, "Keara", "", "", "", "", "", "", "", listOf("The ABC's of Beth"), "", ""),
            Character(
                4,
                "Sleepy Gary",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                listOf("Total Rickall"),
                "",
                ""
            ),
            Character(5, "Pussifer", "", "", "", "", "", "", "", listOf("Rickmurai Jack"), "", ""),
            Character(6, "Keara", "", "", "", "", "", "", "", listOf("The ABC's of Beth"), "", ""),
            Character(
                7,
                "Sleepy Gary",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                listOf("Total Rickall"),
                "",
                ""
            ),
            Character(8, "Pussifer", "", "", "", "", "", "", "", listOf("Rickmurai Jack"), "", "")
        )
    }

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

        adapter.setList(getExampleDataList())

        with(binding) {
            charactersRecyclerView.adapter = adapter
            charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((charactersRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == adapter.itemCount - 1) {
                        adapter.addNewItems(3)
                    }
                }
            })

            buttonLightTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            buttonDarkTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            buttonAutoTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            buttonShuffle.setOnClickListener {
                val copy: MutableList<Character> = ArrayList()
                copy.addAll(adapter.currentList())
                copy.shuffle()
                adapter.shuffleData(copy)
            }
        }
    }

    private fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onCharacterClick(character: Character) {
        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("UserStack")
            .replace(R.id.fragmentContainerView, LocationsFragment())
            .commit()
    }
}
package ru.svoyakmartin.rickandmortyapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharacterBinding

class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharacterBinding

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

        binding.createUser.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("UserStack")
                .replace(R.id.fragmentContainerView, LocationsFragment())
                .commit()
        }
    }
}
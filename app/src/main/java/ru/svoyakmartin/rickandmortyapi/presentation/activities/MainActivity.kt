package ru.svoyakmartin.rickandmortyapi.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.ActivityMainBinding
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.CharactersFragment
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.EpisodesFragment
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.LocationsFragment
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, CharactersFragment())
                .commit()
        }

        with(binding) {
            settingsButton.setOnClickListener { goToFragment(SettingsFragment()) }
            charactersButton.setOnClickListener { goToFragment(CharactersFragment()) }
            locationsButton.setOnClickListener { goToFragment(LocationsFragment()) }
            episodesButton.setOnClickListener { goToFragment(EpisodesFragment()) }
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("UserStack")
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}
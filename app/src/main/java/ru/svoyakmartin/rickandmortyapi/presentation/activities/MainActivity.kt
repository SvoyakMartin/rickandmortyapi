package ru.svoyakmartin.rickandmortyapi.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
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
            goToFragment(CharactersFragment())
        }

        with(binding) {
            settingsButton.setOnClickListener { goToFragment(SettingsFragment(), true) }
            charactersButton.setOnClickListener { goToFragment(CharactersFragment()) }
            locationsButton.setOnClickListener { goToFragment(LocationsFragment()) }
            episodesButton.setOnClickListener { goToFragment(EpisodesFragment()) }
        }
    }

    private fun goToFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val tag = fragment::class.java.name.substringAfterLast('.')
        val container = R.id.fragmentContainerView

        supportFragmentManager.apply {
//            val oldFragment = findFragmentByTag(tag)

            commit {
                setReorderingAllowed(true)

                if (addToBackStack) {
                    addToBackStack("UserStack")
                }

                replace(container, fragment, tag)

                // TODO: додумать переиспользование фрагментов или сохранять скролл в самом фрагменте

//                fragments.forEach {
//                    hide(it)
//                }
//
//                if (oldFragment == null) {
//                    add(container, fragment, tag)
//                } else {
//                    show(oldFragment)
//                }
            }
        }
    }
}
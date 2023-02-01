package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.commit
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentStartBinding
import ru.svoyakmartin.rickandmortyapi.presentation.util.getShortClassName
import kotlin.system.exitProcess

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private var backPressed: Long = 0
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            backPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        callback.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        callback.isEnabled = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        initViews()

        if (childFragmentManager.fragments.isEmpty()) {
            setFragment(CharactersFragment())
        } else {
            setFragmentFromBottomMenu(binding.startFragmentBottomNavigation.selectedItemId)
        }

        return binding.root
    }

    private fun initViews() {
        with(requireActivity()) {
            onBackPressedDispatcher.addCallback(this, callback)
        }

        with(binding) {
            startFragmentBottomNavigation.apply {
                setOnItemSelectedListener { menuItem ->
                    setFragmentFromBottomMenu(menuItem.itemId)

                    true
                }
            }
        }
    }

    private fun setFragmentFromBottomMenu(menuItemId: Int?) {
        if (binding.startFragmentBottomNavigation.selectedItemId != menuItemId) {
            val fragment = when (menuItemId) {
                R.id.start_fragment_bottom_navigation_locations_item -> LocationsFragment()
                R.id.start_fragment_bottom_navigation_episodes_item -> EpisodesFragment()
                R.id.start_fragment_bottom_navigation_settings_item -> SettingsFragment()
                //R.id.start_fragment_bottom_navigation_characters_item ->
                else -> CharactersFragment()
            }

            setFragment(fragment)
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTag = fragment.getShortClassName()

        with(childFragmentManager) {
            var existingFragment = findFragmentByTag(fragmentTag)

            commit {
                setReorderingAllowed(true)
                fragments.forEach { hide(it) }

                if (existingFragment == null) {
                    add(R.id.start_fragment_container, fragment, fragmentTag)
                    existingFragment = fragment
                }

                show(existingFragment!!)
            }
        }
    }

    private fun backPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            requireActivity().apply {
                finishAffinity()
                exitProcess(0)
            }
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.back_pressed_toast_text),
                Toast.LENGTH_SHORT
            ).show()

            backPressed = System.currentTimeMillis()
        }
    }
}
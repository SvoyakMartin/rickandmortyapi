package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val settings = UserPreferencesRepository.getInstance(requireActivity())

        with(binding) {

            themeRadioGroup.setOnCheckedChangeListener { _, id ->
                val mode =
                    when (id) {
                        lightThemeRadioButton.id -> {
                            AppCompatDelegate.MODE_NIGHT_NO
                        }
                        darkThemeRadioButton.id -> {
                            AppCompatDelegate.MODE_NIGHT_YES
                        }
                        else -> {
                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        }
                    }
                settings.apply {
                    setNightMode(mode)
                }
            }

            when (settings.readSavedNightMode()) {
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    lightThemeRadioButton.isChecked = true
                }
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    darkThemeRadioButton.isChecked = true
                }
                else -> {
                    systemThemeRadioButton.isChecked = true
                }
            }
        }
    }
}
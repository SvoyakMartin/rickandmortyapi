package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var settings: UserPreferencesRepository

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

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
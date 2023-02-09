package ru.svoyakmartin.featureSettings.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.databinding.FragmentSettingsBinding
import ru.svoyakmartin.featureSettings.ui.viewModel.SettingsComponentViewModel
import javax.inject.Inject

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var settings: UserPreferencesRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel<SettingsComponentViewModel>().component.inject(this)
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
                settings.setNightMode(mode)
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
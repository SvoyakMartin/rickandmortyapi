package ru.svoyakmartin.featureSettings.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreUI.viewModel
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.databinding.FragmentSettingsBinding
import ru.svoyakmartin.featureSettings.ui.viewModel.SettingsComponentDependenciesProvider
import ru.svoyakmartin.featureSettings.ui.viewModel.SettingsComponentViewModel
import javax.inject.Inject

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var settings: UserPreferencesRepositoryImpl

    override fun onAttach(context: Context) {
        SettingsComponentDependenciesProvider.dependencies = findFeatureExternalDependencies()
        viewModel<SettingsComponentViewModel>().component.inject(this)
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
        initTheme()
        initLanguage()
    }

    private fun initTheme() {
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

    private fun initLanguage() {
        with(binding) {
            languageSpinner.apply {
//                setSelection(1)

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val lang = when (position) {
                            1 -> "en"
                            2 -> "ru"
                            else -> null
                        }
                        settings.setLocale(lang)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
//                {
//                val mode =
//                    when (id) {
//                        lightThemeRadioButton.id -> {
//                            AppCompatDelegate.MODE_NIGHT_NO
//                        }
//                        darkThemeRadioButton.id -> {
//                            AppCompatDelegate.MODE_NIGHT_YES
//                        }
//                        else -> {
//                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//                        }
//                    }
//                }
            }
        }
    }
}

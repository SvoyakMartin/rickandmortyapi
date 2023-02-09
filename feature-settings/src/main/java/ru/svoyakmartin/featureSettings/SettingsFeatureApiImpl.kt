package ru.svoyakmartin.featureSettings

import androidx.fragment.app.Fragment
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.ui.fragment.SettingsFeatureFlowFragment
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject


class SettingsFeatureApiImpl @Inject constructor(val repository: UserPreferencesRepositoryImpl) :
    SettingsFeatureApi {
    override fun getFlowFragment(): Fragment = SettingsFeatureFlowFragment()
    override fun saveInt(key: String, value: Int) = repository.saveInt(key, value)
    override fun readInt(key: String, defaultValue: Int): Int =
        repository.readInt(key, defaultValue)
}
package ru.svoyakmartin.featureSettings

import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.ui.fragment.SettingsFragment
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject


class SettingsFeatureApiImpl @Inject constructor(private val repository: UserPreferencesRepositoryImpl) :
    SettingsFeatureApi {
    override fun getFlowFragment() = SettingsFragment()
    override fun saveInt(key: String, value: Int) = repository.saveInt(key, value)
    override fun readInt(key: String, defaultValue: Int) = repository.readInt(key, defaultValue)
}
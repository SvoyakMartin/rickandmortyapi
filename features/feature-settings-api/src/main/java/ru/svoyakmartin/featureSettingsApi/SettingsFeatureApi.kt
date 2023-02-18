package ru.svoyakmartin.featureSettingsApi

import androidx.fragment.app.Fragment

interface SettingsFeatureApi {
    fun getFlowFragment(): Fragment
    fun saveInt(key: String, value: Int)
    fun readInt(key: String, defaultValue: Int): Int

    companion object {
        const val CHARACTERS_LAST_PAGE_KEY = "charactersLastPage"
        const val LOCATIONS_LAST_PAGE_KEY = "locationsLastPage"
        const val EPISODES_LAST_PAGE_KEY = "episodesLastPage"
        const val BACKSTACK_KEY = "settings"
    }
}
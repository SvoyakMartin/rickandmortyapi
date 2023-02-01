package ru.svoyakmartin.rickandmortyapi.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import ru.svoyakmartin.rickandmortyapi.PREFERENCE_FILE_KEY


class UserPreferencesRepository private constructor(context: Context) {
    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE
        )

    private fun saveNightMode(mode: Int) {
        sharedPreferences.change {
            putInt(NIGHT_MODE_KEY, mode)
        }
    }

    fun readSavedNightMode(): Int {
        return sharedPreferences.getInt(NIGHT_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        saveNightMode(mode)
    }

    fun saveCharactersLastPage(page: Int) {
        sharedPreferences.change {
            putInt(CHARACTERS_LAST_PAGE_KEY, page)
        }
    }

    fun saveEpisodesLastPage(page: Int) {
        sharedPreferences.change {
            putInt(EPISODES_LAST_PAGE_KEY, page)
        }
    }

    fun saveLocationsLastPage(page: Int) {
        sharedPreferences.change {
            putInt(LOCATIONS_LAST_PAGE_KEY, page)
        }
    }

    fun readSavedCharactersLastPage(): Int {
        return sharedPreferences.getInt(CHARACTERS_LAST_PAGE_KEY, 1)
    }

    fun readSavedEpisodesLastPage(): Int {
        return sharedPreferences.getInt(EPISODES_LAST_PAGE_KEY, 1)
    }

    fun readSavedLocationsLastPage(): Int {
        return sharedPreferences.getInt(LOCATIONS_LAST_PAGE_KEY, 1)
    }

    companion object {
        const val NIGHT_MODE_KEY = "nightMode"
        const val CHARACTERS_LAST_PAGE_KEY = "charactersLastPage"
        const val EPISODES_LAST_PAGE_KEY = "charactersLastPage"
        const val LOCATIONS_LAST_PAGE_KEY = "locationsLastPage"

        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private fun SharedPreferences.change(block: SharedPreferences.Editor.() -> Unit) {
        edit().apply {
            block()
            apply()
        }
    }
}
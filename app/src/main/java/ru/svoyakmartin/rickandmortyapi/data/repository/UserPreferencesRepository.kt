package ru.svoyakmartin.rickandmortyapi.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate


class UserPreferencesRepository private constructor(context: Context) {
    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE
        )

    private fun saveNightMode(mode: Int) {
        sharedPreferences.change {
            putInt(THEME_KEY, mode)
        }
    }

    fun readSavedNightMode(): Int {
        return sharedPreferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        saveNightMode(mode)
    }

    companion object {
        const val PREFERENCE_FILE_KEY = "settings"
        const val THEME_KEY = "theme"

        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    fun SharedPreferences.change(block: SharedPreferences.Editor.() -> Unit) {
        edit().apply {
            block()
            apply()
        }
    }
}
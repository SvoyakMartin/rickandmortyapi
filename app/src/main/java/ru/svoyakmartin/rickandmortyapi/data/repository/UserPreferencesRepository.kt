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

    fun saveLastPage(page: Int) {
        sharedPreferences.change {
            putInt(LAST_PAGE_KEY, page)
        }
    }

    fun readSavedLastPage(): Int {
        return sharedPreferences.getInt(LAST_PAGE_KEY, 1)
    }

    companion object {
        const val PREFERENCE_FILE_KEY = "settings"
        const val NIGHT_MODE_KEY = "nightMode"
        const val LAST_PAGE_KEY = "lastPage"

        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        fun getInstance(): UserPreferencesRepository? {
            return INSTANCE

        }

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

    private fun SharedPreferences.change(block: SharedPreferences.Editor.() -> Unit) {
        edit().apply {
            block()
            apply()
        }
    }
}
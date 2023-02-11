package ru.svoyakmartin.featureSettings.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import ru.svoyakmartin.featureSettings.PREFERENCE_FILE_KEY


class UserPreferencesRepositoryImpl private constructor(context: Context) {
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

    fun saveInt(key: String, value: Int){
        sharedPreferences.change {
            putInt(key, value)
        }
    }

    fun readInt(key: String, defaultValue: Int): Int{
        return sharedPreferences.getInt(key, defaultValue)
    }

    companion object {
        const val NIGHT_MODE_KEY = "nightMode"

        @Volatile
        private var INSTANCE: UserPreferencesRepositoryImpl? = null

        fun getInstance(context: Context): UserPreferencesRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }

                val instance = UserPreferencesRepositoryImpl(context)
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
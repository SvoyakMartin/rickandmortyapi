package ru.svoyakmartin.rickandmortyapi

import android.app.Application
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        UserPreferencesRepository.getInstance(this).apply {
            setNightMode(readSavedNightMode())
        }
    }
}
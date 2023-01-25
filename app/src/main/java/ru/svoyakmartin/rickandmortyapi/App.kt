package ru.svoyakmartin.rickandmortyapi

import android.app.Application
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDB
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository

class App : Application() {
    private val database by lazy { RoomDB.getDatabase(this) }
    val repository by lazy { Repository(database.getDao()) }

    override fun onCreate() {
        super.onCreate()
        UserPreferencesRepository.getInstance(this).apply {
            setNightMode(readSavedNightMode())
        }
    }
}
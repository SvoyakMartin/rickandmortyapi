package ru.svoyakmartin.rickandmortyapi

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDB
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository

class App : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RoomDB.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(database.getDao()) }

    override fun onCreate() {
        super.onCreate()
        UserPreferencesRepository.getInstance(this).apply {
            setNightMode(readSavedNightMode())
        }
    }
}
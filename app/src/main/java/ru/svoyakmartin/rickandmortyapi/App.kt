package ru.svoyakmartin.rickandmortyapi

import android.app.Application

import ru.svoyakmartin.rickandmortyapi.di.AppComponent
import ru.svoyakmartin.rickandmortyapi.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .factory()
            .create(this)
            .apply {
                userPreferencesRepository.apply { setNightMode(readSavedNightMode()) }
            }
    }
}
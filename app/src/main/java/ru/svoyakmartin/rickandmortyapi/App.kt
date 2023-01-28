package ru.svoyakmartin.rickandmortyapi

import android.app.Application
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository
import ru.svoyakmartin.rickandmortyapi.di.AppComponent
import ru.svoyakmartin.rickandmortyapi.di.AppModule
import ru.svoyakmartin.rickandmortyapi.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()


        UserPreferencesRepository.getInstance(this).apply {
            setNightMode(readSavedNightMode())
        }
    }
}
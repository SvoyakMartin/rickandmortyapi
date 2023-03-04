package ru.svoyakmartin.rickandmortyapi

import android.app.Application
import androidx.work.Configuration
import ru.svoyakmartin.featureNotification.NotificationWorkerFactory
import ru.svoyakmartin.rickandmortyapi.di.AppComponent
import ru.svoyakmartin.rickandmortyapi.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), Configuration.Provider {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var notificationWorkerFactory: NotificationWorkerFactory

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(applicationContext)
            .apply {
                inject(this@App)
                userPreferencesRepository.apply { setNightMode(readSavedNightMode()) }
            }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(notificationWorkerFactory)
            .build()
}
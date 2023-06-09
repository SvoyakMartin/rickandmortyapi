package ru.svoyakmartin.rickandmortyapi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.coreNetwork.di.NetworkModule
import ru.svoyakmartin.featureCharacter.di.CharacterExternalDependencies
import ru.svoyakmartin.featureCharacterDependencies.di.CharacterDependenciesExternalDependencies
import ru.svoyakmartin.featureEpisode.di.EpisodeExternalDependencies
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenExternalDependencies
import ru.svoyakmartin.featureLocation.di.LocationExternalDependencies
import ru.svoyakmartin.featureNotification.di.NotificationExternalDependencies
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.di.SettingsExternalDependencies
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.AppActivity
import ru.svoyakmartin.rickandmortyapi.di.modules.*

@[AppScope Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        NavigationModule::class,
        FeatureExternalDependenciesModule::class
    ]
)]
interface AppComponent :
    HomeScreenExternalDependencies,
    CharacterExternalDependencies,
    CharacterDependenciesExternalDependencies,
    SettingsExternalDependencies,
    LocationExternalDependencies,
    EpisodeExternalDependencies {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    val userPreferencesRepository: UserPreferencesRepositoryImpl

    fun inject(activity: AppActivity)
    fun inject(application: App)
}
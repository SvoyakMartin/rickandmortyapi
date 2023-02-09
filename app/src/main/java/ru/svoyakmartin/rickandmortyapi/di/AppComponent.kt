package ru.svoyakmartin.rickandmortyapi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.coreFlow.di.FlowModule
import ru.svoyakmartin.featureCharacter.di.CharacterFeatureApiModule
import ru.svoyakmartin.featureCharacter.di.CharacterExternalDependencies
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenExternalDependencies
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenFeatureApiModule
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.di.SettingsExternalDependencies
import ru.svoyakmartin.featureSettings.di.SettingsFeatureApiModule
import ru.svoyakmartin.rickandmortyapi.AppActivity
import ru.svoyakmartin.rickandmortyapi.di.modules.*
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.*

@[AppScope Component(
    modules = [
        AppModule::class,
        FlowModule::class,
        NetworkModule::class,
        NavigationModule::class,
        ViewModelsModule::class,
        FeatureExternalDependenciesModule::class,
        HomeScreenFeatureApiModule::class,
        CharacterFeatureApiModule::class,
        SettingsFeatureApiModule::class
    ]
)]
interface AppComponent :
    HomeScreenExternalDependencies,
    CharacterExternalDependencies,
    SettingsExternalDependencies {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    val userPreferencesRepository: UserPreferencesRepositoryImpl

    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: EpisodeDetailsFragment)

    fun inject(fragment: LocationsFragment)
    fun inject(fragment: LocationDetailsFragment)

    fun inject(activity: AppActivity)
}
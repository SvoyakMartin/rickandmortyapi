package ru.svoyakmartin.rickandmortyapi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.coreFlow.di.FlowModule
import ru.svoyakmartin.featureCharacter.di.CharacterExternalDependencies
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenExternalDependencies
import ru.svoyakmartin.featureLocation.di.LocationExternalDependencies
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import ru.svoyakmartin.featureSettings.di.SettingsExternalDependencies
import ru.svoyakmartin.rickandmortyapi.AppActivity
import ru.svoyakmartin.rickandmortyapi.di.modules.*

@[AppScope Component(
    modules = [
        AppModule::class,
        FlowModule::class,
        NetworkModule::class,
        NavigationModule::class,
        FeatureExternalDependenciesModule::class
    ]
)]
interface AppComponent :
    HomeScreenExternalDependencies,
    CharacterExternalDependencies,
    SettingsExternalDependencies,
    LocationExternalDependencies {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    val userPreferencesRepository: UserPreferencesRepositoryImpl

    fun inject(activity: AppActivity)
}
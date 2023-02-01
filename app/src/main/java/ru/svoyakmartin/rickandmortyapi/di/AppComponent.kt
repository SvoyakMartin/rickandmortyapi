package ru.svoyakmartin.rickandmortyapi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository
import ru.svoyakmartin.rickandmortyapi.di.annotations.AppScope
import ru.svoyakmartin.rickandmortyapi.di.modules.AppModule
import ru.svoyakmartin.rickandmortyapi.di.modules.NetworkModule
import ru.svoyakmartin.rickandmortyapi.di.modules.ViewModelsModule
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.*

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelsModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    val userPreferencesRepository: UserPreferencesRepository

    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailsFragment)

    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: EpisodeDetailsFragment)

    fun inject(fragment: LocationsFragment)
    fun inject(fragment: LocationDetailsFragment)

    fun inject(fragment: SettingsFragment)
}
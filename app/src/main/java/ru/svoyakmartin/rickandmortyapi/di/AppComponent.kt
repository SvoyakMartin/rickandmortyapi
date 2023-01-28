package ru.svoyakmartin.rickandmortyapi.di

import android.content.Context
import dagger.Component
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.ApiService
import ru.svoyakmartin.rickandmortyapi.data.repository.Repository
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.CharacterDetailsFragment
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.CharactersFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent{
    fun getAppContext(): Context
    fun getRoomDAO(): RoomDAO
    fun getApiService(): ApiService
    fun getRepository(): Repository

    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailsFragment)
}
package ru.svoyakmartin.rickandmortyapi.di

import android.app.Application
import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    fun getAppContext(): Context
}
package ru.svoyakmartin.rickandmortyapi.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDB
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, NetworkModule::class])
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application
    }
    @Provides
    @Singleton
    fun provideRoomDB(): RoomDB {
        return RoomDB.getDatabase(application)
    }


}
package ru.svoyakmartin.rickandmortyapi.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDB
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRoomDAO(): RoomDAO {
        return RoomDB.getDatabase(application).getDao()
    }


}
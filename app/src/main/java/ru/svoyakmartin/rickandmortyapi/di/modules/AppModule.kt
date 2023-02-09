package ru.svoyakmartin.rickandmortyapi.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDB
import ru.svoyakmartin.coreDi.di.scope.AppScope


@Module
class AppModule {
    @[AppScope Provides]
    fun provideRoomDAO(context: Context): RoomDAO {
        return RoomDB.getDatabase(context).getDao()
    }
}
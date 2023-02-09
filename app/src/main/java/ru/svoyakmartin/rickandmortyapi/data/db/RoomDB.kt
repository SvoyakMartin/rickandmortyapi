package ru.svoyakmartin.rickandmortyapi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.svoyakmartin.rickandmortyapi.DB_NAME
import ru.svoyakmartin.rickandmortyapi.data.db.models.*

@Database(
    entities = [ru.svoyakmartin.featureCharacter.domain.model.Character::class, Episode::class, Location::class, CharactersAndEpisodes::class, CharactersAndLocations::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {

    abstract fun getDao(): RoomDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase (context: Context): RoomDB {
            return (INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = instance

                instance
            })
        }
    }
}
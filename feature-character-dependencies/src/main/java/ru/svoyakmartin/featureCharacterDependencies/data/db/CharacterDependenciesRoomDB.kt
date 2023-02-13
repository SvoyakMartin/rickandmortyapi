package ru.svoyakmartin.featureCharacterDependencies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.svoyakmartin.featureCharacterDependencies.DB_NAME
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndEpisodes
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndLocations

@Database(
    entities = [CharactersAndEpisodes::class, CharactersAndLocations::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDependenciesRoomDB : RoomDatabase() {
    abstract fun getCharacterDependenciesDao(): CharacterDependenciesRoomDAO

    companion object {
        @Volatile
        private var INSTANCE: CharacterDependenciesRoomDB? = null

        fun getDatabase(context: Context): CharacterDependenciesRoomDB {
            return (INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDependenciesRoomDB::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = instance

                instance
            })
        }
    }
}
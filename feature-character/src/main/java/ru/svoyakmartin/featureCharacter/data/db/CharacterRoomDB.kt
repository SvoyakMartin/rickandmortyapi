package ru.svoyakmartin.featureCharacter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.svoyakmartin.featureCharacter.DB_NAME
import ru.svoyakmartin.featureCharacter.domain.model.Character

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterRoomDB : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterRoomDAO

    companion object {
        @Volatile
        private var INSTANCE: CharacterRoomDB? = null

        fun getDatabase (context: Context): CharacterRoomDB {
            return (INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterRoomDB::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = instance

                instance
            })
        }
    }
}
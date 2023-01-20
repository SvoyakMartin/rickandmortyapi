package ru.svoyakmartin.rickandmortyapi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.domain.models.Character
import ru.svoyakmartin.rickandmortyapi.domain.models.Episode
import ru.svoyakmartin.rickandmortyapi.domain.models.Location

@Database(
    entities = [Character::class, Episode::class, Location::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {

    abstract fun getDao(): RoomDAO

    private class RoomDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.getDao())
                }
            }
        }

        suspend fun populateDatabase(roomDAO: RoomDAO) {
            roomDAO.deleteAllCharacters()

            for (i in 1..10) {
                val character = Character(i, "demo Name", "", "", "", "", "", "", "")
                roomDAO.insertCharacter(character)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RoomDB {
            return (INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "DB.db"
                )
                    .addCallback(RoomDBCallback(scope))
                    .build()
                INSTANCE = instance

                instance
            })
        }
    }
}
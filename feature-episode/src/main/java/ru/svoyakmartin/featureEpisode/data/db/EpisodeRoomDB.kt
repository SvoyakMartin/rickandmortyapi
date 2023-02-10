package ru.svoyakmartin.featureEpisode.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.svoyakmartin.featureEpisode.DB_NAME
import ru.svoyakmartin.featureEpisode.domain.model.Episode

@Database(
    entities = [Episode::class],
    version = 1,
    exportSchema = false
)
abstract class EpisodeRoomDB : RoomDatabase() {

    abstract fun getEpisodeDao(): EpisodeRoomDAO

    companion object {
        @Volatile
        private var INSTANCE: EpisodeRoomDB? = null

        fun getDatabase (context: Context): EpisodeRoomDB {
            return (INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EpisodeRoomDB::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = instance

                instance
            })
        }
    }
}
package ru.svoyakmartin.featureLocation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.svoyakmartin.featureLocation.DB_NAME
import ru.svoyakmartin.featureLocation.domain.model.Location

@Database(
    entities = [Location::class],
    version = 1,
    exportSchema = false
)
abstract class LocationRoomDB : RoomDatabase() {
    abstract fun getLocationDao(): LocationRoomDAO

    companion object {
        @Volatile
        private var INSTANCE: LocationRoomDB? = null

        fun getDatabase (context: Context): LocationRoomDB {
            return (INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationRoomDB::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = instance

                instance
            })
        }
    }
}
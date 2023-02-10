package ru.svoyakmartin.featureLocation.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureLocation.LOCATIONS_TABLE_NAME
import ru.svoyakmartin.featureLocation.domain.model.Location


@Dao
interface LocationRoomDAO {
    @Query("SELECT * FROM $LOCATIONS_TABLE_NAME")
    fun getAllLocations(): Flow<List<Location>?>

    @Query("SELECT * FROM $LOCATIONS_TABLE_NAME WHERE id = :locationId")
    fun getLocationById(locationId: Int): Flow<Location?>

    @Query("SELECT name FROM $LOCATIONS_TABLE_NAME WHERE id = :locationId")
    fun getLocationNameById(locationId: Int): Flow<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)
}
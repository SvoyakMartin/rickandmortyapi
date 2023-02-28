package ru.svoyakmartin.featureLocation.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import ru.svoyakmartin.featureLocation.LOCATIONS_TABLE_NAME
import ru.svoyakmartin.featureLocation.domain.model.Location


@Dao
interface LocationRoomDAO {
    @Query("SELECT * FROM $LOCATIONS_TABLE_NAME")
    fun getAllLocations(): Flow<List<Location>>

    @Query("SELECT * FROM $LOCATIONS_TABLE_NAME WHERE id = :locationId")
    fun getLocationById(locationId: Int): Flow<Location?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)

    @Query(
        "SELECT id \n" +
                "FROM $LOCATIONS_TABLE_NAME \n" +
                "WHERE id in (:locationIdsList)"
    )
    fun getExistingLocationIds(locationIdsList: Set<Int>): Flow<List<Int>>

    @Query(
        "SELECT id, name \n" +
                "FROM $LOCATIONS_TABLE_NAME \n" +
                "WHERE id in (:locationIdsList)"
    )
    fun getLocationsNameByIds(locationIdsList: Set<Int>): Flow<List<EntityMap>>

}
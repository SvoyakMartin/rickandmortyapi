package ru.svoyakmartin.featureLocation.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import ru.svoyakmartin.featureLocation.data.dataSource.toLocation
import ru.svoyakmartin.featureLocation.domain.model.Location
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class LocationRoomDBTest : TestCase() {
    private lateinit var dao: LocationRoomDAO
    private lateinit var db: LocationRoomDB

    private val location = Location(
        id = 1,
        name = "Earth",
        type = "Planet",
        dimension = "Dimension C-137"
    )
    private val locationTwo = Location(
        id = 2,
        name = "Abadango",
        type = "Cluster",
        dimension = "unknown"
    )

    private val locationList = listOf(location, locationTwo)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LocationRoomDB::class.java).build()
        dao = db.getLocationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocation() = runBlocking {
        // Given
        val expected = location
        dao.insertLocation(expected)
        dao.getLocationById(expected.id).collect { locationById ->
            // When
            val actual = locationById
            // Then
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocationList() = runBlocking {
        // Given
        val expected = 2
        dao.insertLocations(locationList)
        dao.getAllLocations().collect { locationList ->
            // When
            val actual = locationList?.size ?: 0
            // Then
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadExistingLocationIds() = runBlocking {
        // Given
        val expected = listOf(1, 2)
        val locationIds = listOf(1, 2, 5, 10, 20)

        dao.insertLocations(locationList)
        dao.getExistingLocationIds(locationIds).collect { idsList ->
            // When
            val actual = idsList
            // Then
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocationsNameByIds() = runBlocking {
        // Given
        val expected = listOf(EntityMap(1, "Earth"), EntityMap(2, "Abadango"))
        val locationIds = listOf(1, 2, 5, 10, 20)

        dao.insertLocations(locationList)
        dao.getLocationsNameByIds(locationIds).collect { mapList ->
            // When
            val actual = mapList
            // Then
            Assert.assertEquals(expected, actual)
        }
    }
}
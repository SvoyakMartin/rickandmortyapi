package ru.svoyakmartin.featureLocation.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.*
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import ru.svoyakmartin.featureLocation.domain.model.Location
import java.io.IOException

@ExperimentalCoroutinesApi
class LocationRoomDBTest {
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
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocationRoomDB::class.java
        )
            .build()
        dao = db.getLocationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocation() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val expected = location
        launch { dao.insertLocation(expected) }
        // When
        val actual = dao.getLocationById(expected.id).first()
        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocationList() = runTest {
        // Given
        val expected = 2
        launch { dao.insertLocations(locationList) }
        advanceUntilIdle()
        // When
        val actual = dao.getAllLocations().first()?.size
        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadExistingLocationIds() = runTest {
        // Given
        val expected = listOf(1, 2)
        val locationIds = listOf(1, 2, 5, 10, 20)
        launch { dao.insertLocations(locationList) }
        advanceUntilIdle()
        // When
        val actual = dao.getExistingLocationIds(locationIds).first()
        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocationsNameByIds() = runTest {
        // Given
        val expected = listOf(EntityMap(1, "Earth"), EntityMap(2, "Abadango"))
        val locationIds = listOf(1, 2, 5, 10, 20)
        launch { dao.insertLocations(locationList) }
        advanceUntilIdle()
        // When
        val actual = dao.getLocationsNameByIds(locationIds).first()
        // Then
        Assert.assertEquals(expected, actual)

    }
}
package ru.svoyakmartin.featureLocation.data.dataSource

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LocationsApiTest {

    @Mock
    lateinit var apiService: LocationsApi

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `get locations response code by page number`() = runBlocking {
        // Given
        val expected = 200
        // When
        val response = apiService.getLocations(1)
        val actual = response.code()
        // Then
        assertEquals(expected, actual)
    }
}
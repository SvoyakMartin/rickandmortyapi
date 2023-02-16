package ru.svoyakmartin.featureLocation.ui.viewModel

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.svoyakmartin.featureCore.data.model.InfoDTO
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.model.LocationsDTO

internal class LocationListViewModelTest {

    fun setUp() {
    }

    fun tearDown() {
    }

    fun getAllLocations() {
    }

    fun isLoading() {
    }

    fun setIsLoading() {
    }

    fun fetchNextLocationsPartFromWeb() {
    }

    @Mock
    lateinit var apiService : LocationsApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `get locations response code by page number`() = runTest {
        // Given
        val expected = 200
        var response = Response.success(200, LocationsDTO(InfoDTO(1,1,"",""), listOf()))

        Mockito.`when`(apiService.getLocations(1)).thenReturn(response)
        response = apiService.getLocations(1)

        // When
        val actual = response.code()
        // Then
        Assert.assertEquals(expected, actual)
    }

}
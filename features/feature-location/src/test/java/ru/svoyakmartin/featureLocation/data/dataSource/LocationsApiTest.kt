package ru.svoyakmartin.featureLocation.data.dataSource

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest
import retrofit2.create
import ru.svoyakmartin.coreNetwork.provider.HttpLoggingInterceptorProvider
import ru.svoyakmartin.coreNetwork.provider.JsonConverterFactoryProvider
import ru.svoyakmartin.coreNetwork.provider.OkHttpClientProvider
import ru.svoyakmartin.coreNetwork.provider.RetrofitProvider

@ExperimentalCoroutinesApi
class LocationsApiTest {
    private val apiService: LocationsApi = RetrofitProvider.get(
        "https://rickandmortyapi.com/api/",
        OkHttpClientProvider.get(setOf(HttpLoggingInterceptorProvider.get())),
        JsonConverterFactoryProvider.get()
    ).create()

    @Test
    fun `get locations response code by page number`() = runTest {
        // Given
        val expected = 200
        val response = apiService.getLocations(1)

        // When
        val actual = response.code()
        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `get locations result size by page number`() = runTest {
        // Given
        val expected = 20
        val response = apiService.getLocations(1)

        // When
        val actual = response.body()?.results?.size ?: 0
        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `get locations response code by id`() = runTest {
        // Given
        val expected = 200
        val response = apiService.getLocationById(1)

        // When
        val actual = response.code()
        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `get location name by id`() = runTest {
        // Given
        val expected = "Earth (C-137)"
        val response = apiService.getLocationById(1)

        // When
        val actual = response.body()?.name ?: ""
        // Then
        assertEquals(expected, actual)
    }


    @Test
    fun `get locations size by ids`() = runTest {
        // Given
        val expected = 3
        val response = apiService.getLocationsByIds("1,2,3")

        // When
        val actual = response.body()?.size ?: 0
        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `get locations ids by ids`() = runTest {
        // Given
        val expected = true
        val response = apiService.getLocationsByIds("1,5,10")

        // When
        val list = response.body() ?: listOf()
        val actual = list.size == 3
                && list[0].id == 1
                && list[1].id == 5
                && list[2].id == 10
        // Then
        assertEquals(expected, actual)
    }
}
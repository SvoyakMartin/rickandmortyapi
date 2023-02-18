package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.domain.model.Getter

@ExperimentalCoroutinesApi
internal class ExportRepositoryImplTest {
    @Mock
    private lateinit var locationRoomDAO: LocationRoomDAO

    @Mock
    private lateinit var apiService: LocationsApi

    @Mock
    private lateinit var characterDependenciesFeatureApi: CharacterDependenciesFeatureApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `getLocationMapByIds all from DB`() = runTest {
        // Given
        val expected = 3
        val locationIdsList = listOf(1, 2, 3)
        val entityMapList = Getter.getEntityMapList()

        `when`(locationRoomDAO.getExistingLocationIds(locationIdsList)).thenReturn(flow {
            emit(locationIdsList)
        })
        `when`(locationRoomDAO.getLocationsNameByIds(locationIdsList)).thenReturn(flow {
            emit(entityMapList)
        })

        val repository =
            ExportRepositoryImpl(locationRoomDAO, apiService, characterDependenciesFeatureApi)

        // When
        val actual = repository.getLocationMapByIds(locationIdsList).first().size
        // Then
        verify(apiService, times(0)).getLocationsByIds(anyString())
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getLocationMapByIds all from API`() = runTest {
        // Given
        val expected = 3
        val locationIdsList = listOf(1, 2, 3)
        val entityMapList = Getter.getEntityMapList()

        `when`(locationRoomDAO.getExistingLocationIds(locationIdsList)).thenReturn(flow {
            emit(listOf())
        })
        `when`(locationRoomDAO.getLocationsNameByIds(locationIdsList)).thenReturn(flow {
            emit(entityMapList)
        })
        `when`(apiService.getLocationsByIds(locationIdsList.joinToString())).thenReturn(
            Response.success(200, Getter.getLocationDTOList())
        )
        val repository =
            ExportRepositoryImpl(locationRoomDAO, apiService, characterDependenciesFeatureApi)

        // When
        val actual = repository.getLocationMapByIds(locationIdsList).first().size
        // Then
        verify(locationRoomDAO, times(1)).insertLocations(anyList())
        Assert.assertEquals(expected, actual)
    }
}
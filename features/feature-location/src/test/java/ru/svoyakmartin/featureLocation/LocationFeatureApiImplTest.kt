package ru.svoyakmartin.featureLocation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.svoyakmartin.featureLocation.data.ExportRepositoryImpl
import ru.svoyakmartin.featureLocation.domain.model.Getter

class LocationFeatureApiImplTest {
    @Mock
    private lateinit var repository: ExportRepositoryImpl

    private lateinit var locationFeatureApi: LocationFeatureApiImpl

    @Before
    fun setup() {
        val locationIdsList = setOf(1, 2, 3)

        MockitoAnnotations.initMocks(this)
        `when`(repository.getLocationMapByIds(locationIdsList)).thenReturn(flow {
            emit(Getter.getEntityMapList())
        })

        locationFeatureApi = LocationFeatureApiImpl(repository)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getLocationMapByIds() = runTest {
        // Given
        val expected = Getter.getEntityMapList()
        // When
        val actual = locationFeatureApi.getLocationMapByIds(setOf(1, 2, 3)).first()
        // Then
        Assert.assertEquals(expected, actual)
    }
}
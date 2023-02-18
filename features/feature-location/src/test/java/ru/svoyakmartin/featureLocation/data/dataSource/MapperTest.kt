package ru.svoyakmartin.featureLocation.data.dataSource

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.svoyakmartin.featureLocation.data.model.LocationDTO
import ru.svoyakmartin.featureLocation.domain.model.Location

class MapperTest {
    private val locationDTO = LocationDTO(
        id = 1,
        name = "Earth",
        type = "Planet",
        dimension = "Dimension C-137",
        residents = listOf(
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2"
        )
    )

    @Test
    fun `toLocation() return type`(){
        // Given
        locationDTO
        // When
        val location = locationDTO.toLocation()
        // Then
        assert(location is Location)
    }

    @Test
    fun `getCharactersIds() return type`(){
        // Given
        locationDTO
        // When
        val residents = locationDTO.getCharactersIds()
        // Then
        assert(residents is List)
    }

    @Test
    fun `getCharactersIds() return size`(){
        // Given
        val expected = 2
        // When
        val actual = locationDTO.getCharactersIds().size
        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `getCharactersIds() items return type`(){
        // Given
        locationDTO
        // When
        val resident = locationDTO.getCharactersIds()[0]
        // Then
        assert(resident is Int)
    }
}
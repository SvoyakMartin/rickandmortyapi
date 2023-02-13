package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.dataSource.getCharactersIds
import ru.svoyakmartin.featureLocation.data.dataSource.toLocation
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.domain.model.Location
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val locationRoomDAO: LocationRoomDAO,
    private val apiService: LocationsApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
) {

    fun getLocationMapByIds(locationIdsList: List<Int>) = flow {
        locationRoomDAO.getExistingLocationIds(locationIdsList)
            .collect { existingLocationIdsList ->
                val difference = locationIdsList.minus(existingLocationIdsList)

                if (difference.isNotEmpty()) {
                    fetchLocationsByIds(difference.joinToString())
                }

                locationRoomDAO.getLocationsNameByIds(locationIdsList).collect { emit(it) }
            }
    }

    private suspend fun fetchLocationsByIds(ids: String) {
        val response = apiService.getLocationsByIds(ids)
        response.body()?.apply {
            val locationsList = ArrayList<Location>()
            val locationsAndCharactersIdsMap = mutableMapOf<Int, List<Int>>()

            forEach { locationDTO ->
                val location = locationDTO.toLocation()
                locationsList.add(location)
                locationsAndCharactersIdsMap[location.id] = locationDTO.getCharactersIds()
            }

            locationRoomDAO.insertLocations(locationsList)
            characterDependenciesFeatureApi.insertLocationsAndCharacters(locationsAndCharactersIdsMap)
        }
    }
}
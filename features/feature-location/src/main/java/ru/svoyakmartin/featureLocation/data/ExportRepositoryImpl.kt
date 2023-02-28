package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
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

    fun getLocationMapByIds(locationIdsList: Set<Int>) = flow {
        locationRoomDAO.getExistingLocationIds(locationIdsList)
            .collect { existingLocationIdsList ->
                val difference = locationIdsList.minus(existingLocationIdsList.toSet())

                if (difference.isNotEmpty()) {
                    fetchLocationsByIds(difference.joinToString()).collect { response ->
                        emit(response)
                    }
                }

                locationRoomDAO.getLocationsNameByIds(locationIdsList).collect { emit(it) }
            }
    }

    private suspend fun fetchLocationsByIds(ids: String) = flow {
        when (val response = apiService.getLocationsByIds(ids)) {
            is ApiResponse.Success -> {
                val locationsList = ArrayList<Location>()
                val locationsAndCharactersIdsMap = mutableMapOf<Int, Set<Int>>()

                response.data.forEach { locationDTO ->
                    val location = locationDTO.toLocation()
                    locationsList.add(location)
                    locationsAndCharactersIdsMap[location.id] = locationDTO.getCharactersIds()
                }

                locationRoomDAO.insertLocations(locationsList)
                characterDependenciesFeatureApi.insertLocationsAndCharacters(
                    locationsAndCharactersIdsMap
                )
            }
            else -> {
                emit(response)
            }
        }
    }
}
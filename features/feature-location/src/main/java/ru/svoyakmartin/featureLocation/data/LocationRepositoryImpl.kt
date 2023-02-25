package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.dataSource.getCharactersIds
import ru.svoyakmartin.featureLocation.data.dataSource.toLocation
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRoomDAO: LocationRoomDAO,
    private val settings: SettingsFeatureApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi,
    private val characterFeatureApi: CharacterFeatureApi,
    private val apiService: LocationsApi,
    statisticFeatureApi: StatisticFeatureApi
) {
    val allLocations = locationRoomDAO.getAllLocations()
    val locationsCount = statisticFeatureApi.getLocationsCount()
    private var locationsLastPage = settings.readInt(SettingsFeatureApi.LOCATIONS_LAST_PAGE_KEY, 1)

    suspend fun fetchNextLocationsPartFromWeb() {
        val response = apiService.getLocations(locationsLastPage)
        response.body()?.apply {
            val locationsList = ArrayList<Location>()
            val locationsAndCharactersIdsMap = mutableMapOf<Int, List<Int>>()

            results.forEach { locationsDTO ->
                val location = locationsDTO.toLocation()
                locationsList.add(location)
                locationsAndCharactersIdsMap[location.id] = locationsDTO.getCharactersIds()
            }

            locationRoomDAO.insertLocations(locationsList)
            characterDependenciesFeatureApi.insertLocationsAndCharacters(locationsAndCharactersIdsMap)

            if (info.pages > locationsLastPage) {
                settings.saveInt(SettingsFeatureApi.LOCATIONS_LAST_PAGE_KEY, ++locationsLastPage)
            }
        }
    }

    fun getLocationById(locationId: Int) = flow {
        locationRoomDAO.getLocationById(locationId)
            .collect { location ->
                if (location != null) {
                    emit(location)
                } else {
                    fetchLocationById(locationId).collect {
                        emit(it)
                    }
                }
            }
    }

    private suspend fun fetchLocationById(id: Int) = flow {
        val response = apiService.getLocationById(id)

        response.body()?.apply {
            val location = toLocation()

            locationRoomDAO.insertLocation(location)

            emit(location)
        }
    }

    suspend fun getCharacterMapByLocationId(locationId: Int) = flow {
        characterDependenciesFeatureApi.getCharactersIdsByLocationId(locationId).collect{
            characterFeatureApi.getCharacterMapByIds(it).collect{emit(it)}
        }
    }
}
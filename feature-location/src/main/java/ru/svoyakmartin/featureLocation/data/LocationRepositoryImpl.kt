package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.dataSource.toLocation
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRoomDAO: LocationRoomDAO,
    private val settings: SettingsFeatureApi,
    private val apiService: LocationsApi
) {
    val allLocations = locationRoomDAO.getAllLocations()
    private var locationsLastPage = settings.readInt(SettingsFeatureApi.LOCATIONS_LAST_PAGE_KEY, 1)

    suspend fun fetchNextLocationsPartFromWeb() {
        val response = apiService.getLocations(locationsLastPage)
        response.body()?.apply {
            val locationsList = ArrayList<Location>()

            results.forEach { locationsDTO ->
                locationsList.add(locationsDTO.toLocation())
            }

            locationRoomDAO.insertLocations(locationsList)

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

    fun getLocationMapById(locationId: Int) = flow {
        locationRoomDAO.getLocationNameById(locationId)
            .collect { name ->
                if (name != null) {
                    emit(mapOf(name to locationId))
                } else {
                    fetchLocationById(locationId).collect {location ->
                        emit(mapOf(location.name to locationId))
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
}
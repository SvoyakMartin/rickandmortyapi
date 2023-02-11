package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.dataSource.toLocation
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val locationRoomDAO: LocationRoomDAO,
    private val apiService: LocationsApi
) {

    fun getLocationMapById(locationId: Int) = flow {
        locationRoomDAO.getLocationNameById(locationId)
            .collect { name ->
                if (name != null) {
                    emit(mapOf(name to locationId))
                } else {
                    fetchLocationById(locationId).collect { location ->
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
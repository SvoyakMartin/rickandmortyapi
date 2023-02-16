package ru.svoyakmartin.featureLocation.data.model

import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureCore.data.model.InfoDTO

@Serializable
data class LocationsDTO(
    var info: InfoDTO,
    var results: List<LocationDTO>,
)
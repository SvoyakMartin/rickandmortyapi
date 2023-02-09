package ru.svoyakmartin.rickandmortyapi.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationsDTO(
    @SerialName("info")
    var info: ru.svoyakmartin.featureIinfo.InfoDTO,

    @SerialName("results")
    var results: List<LocationDTO>,
)
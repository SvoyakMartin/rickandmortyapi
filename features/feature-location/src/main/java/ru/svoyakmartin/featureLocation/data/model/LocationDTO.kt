package ru.svoyakmartin.featureLocation.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    @SerialName("id")
    var id: Int,
    @SerialName("name")
    var name: String,
    @SerialName("type")
    var type: String,
    @SerialName("dimension")
    var dimension: String,
    @SerialName("residents")
    var residents: List<String>
)
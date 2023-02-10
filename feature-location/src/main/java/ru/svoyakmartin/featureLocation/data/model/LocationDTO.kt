package ru.svoyakmartin.featureLocation.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>,
    var url: String,
    var created: String
)
package ru.svoyakmartin.rickandmortyapi.data.remote.models

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
    var residents: List<String>,

    @SerialName("url")
    var url: String,

    @SerialName("created")
    var created: String
    )
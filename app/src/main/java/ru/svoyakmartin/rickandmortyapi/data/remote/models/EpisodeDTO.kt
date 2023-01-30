package ru.svoyakmartin.rickandmortyapi.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDTO(
    @SerialName("id")
    var id: Int,

    @SerialName("name")
    var name: String,

    @SerialName("air_date")
    var airDate: String,

    @SerialName("episode")
    var episode: String,

    @SerialName("characters")
    var characters: List<String>,

    @SerialName("url")
    var url: String,

    @SerialName("created")
    var created: String
    )
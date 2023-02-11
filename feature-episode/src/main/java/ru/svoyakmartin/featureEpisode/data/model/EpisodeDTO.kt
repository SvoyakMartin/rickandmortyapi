package ru.svoyakmartin.featureEpisode.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDTO(
    var id: Int,
    var name: String,
    @SerialName("air_date")
    var airDate: String,
    var episode: String,
    var characters: List<String>
)
package ru.svoyakmartin.rickandmortyapi.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodesDTO(
    @SerialName("info")
    var info: InfoDTO,

    @SerialName("results")
    var results: List<EpisodeDTO>,
)
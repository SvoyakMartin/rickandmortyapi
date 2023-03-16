package ru.svoyakmartin.featureEpisode.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureCore.data.model.InfoDTO

@Serializable
data class EpisodesDTO(
    @SerialName("info")
    var info: InfoDTO,
    @SerialName("results")
    var results: List<EpisodeDTO>,
)
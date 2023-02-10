package ru.svoyakmartin.featureEpisode.data.model

import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureIinfo.InfoDTO

@Serializable
data class EpisodesDTO(
    var info: InfoDTO,
    var results: List<EpisodeDTO>,
)
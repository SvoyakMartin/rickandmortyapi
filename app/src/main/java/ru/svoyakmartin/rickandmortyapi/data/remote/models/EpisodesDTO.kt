package ru.svoyakmartin.rickandmortyapi.data.remote.models

import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureIinfo.InfoDTO

@Serializable
data class EpisodesDTO(
    var info: InfoDTO,
    var results: List<EpisodeDTO>,
)
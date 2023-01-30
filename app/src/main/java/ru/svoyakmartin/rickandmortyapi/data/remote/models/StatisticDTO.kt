package ru.svoyakmartin.rickandmortyapi.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Characters(
    @SerialName("info")
    val info: Info
)

@Serializable
data class Data(
    val stats: Stats
)

@Serializable
data class Episodes(
    val info: Info
)

@Serializable
data class Info(
    val count: Int
)

@Serializable
data class Locations(
    val info: Info
)

@Serializable
data class Root(
    val data: Data
)

@Serializable
data class Stats(
    val characters: Characters,
    val locations: Locations,
    val episodes: Episodes
)

package ru.svoyakmartin.featureStatistic.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Root(
    val data: Data
)

@Serializable
data class Data(
    val stats: Stats
)

@Serializable
data class Stats(
    val characters: Characters,
    val locations: Locations,
    val episodes: Episodes
)

@Serializable
data class Characters(
    val info: Info
)

@Serializable
data class Locations(
    val info: Info
)

@Serializable
data class Episodes(
    val info: Info
)

@Serializable
data class Info(
    val count: Int
)
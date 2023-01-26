package ru.svoyakmartin.rickandmortyapi.data.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Status{
    @SerialName("Alive")
    ALIVE,
    @SerialName("Dead")
    DEAD,
    @SerialName("unknown")
    UNKNOWN
}
package ru.svoyakmartin.featureCharacter.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    @SerialName("Alive")
    ALIVE,

    @SerialName("Dead")
    DEAD,

    @SerialName("unknown")
    UNKNOWN
}
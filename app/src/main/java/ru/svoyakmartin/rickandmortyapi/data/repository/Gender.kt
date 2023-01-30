package ru.svoyakmartin.rickandmortyapi.data.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    @SerialName("Female")
    FEMALE,

    @SerialName("Male")
    MALE,

    @SerialName("Genderless")
    GENDERLESS,

    @SerialName("unknown")
    UNKNOWN
}
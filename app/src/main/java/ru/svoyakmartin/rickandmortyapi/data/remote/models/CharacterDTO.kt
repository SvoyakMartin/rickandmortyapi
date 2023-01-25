package ru.svoyakmartin.rickandmortyapi.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    @SerialName("id")
    var id: Int,

    @SerialName("name")
    var name: String,

    @SerialName("status")
    var status: Status,

    @SerialName("species")
    var species: String,

    @SerialName("type")
    var type: String,

    @SerialName("gender")
    var gender: Gender,

    @SerialName("origin")
    var origin: AdditionalFieldDTO,

    @SerialName("location")
    var location: AdditionalFieldDTO,

    @SerialName("image")
    var image: String,

    @SerialName("episode")
    var episode: List<String>,

    @SerialName("url")
    var url: String,

    @SerialName("created")
    var created: String
    )

@Serializable
data class AdditionalFieldDTO(
    @SerialName("name")
    var name: String,

    @SerialName("url")
    var url: String
)

@Serializable
enum class Status{
    @SerialName("Alive")
    ALIVE,
    @SerialName("Dead")
    DEAD,
    @SerialName("unknown")
    UNKNOWN
}

@Serializable
enum class Gender{
    @SerialName("Female")
    FEMALE,
    @SerialName("Male")
    MALE,
    @SerialName("Genderless")
    GENDERLESS,
    @SerialName("unknown")
    UNKNOWN
}
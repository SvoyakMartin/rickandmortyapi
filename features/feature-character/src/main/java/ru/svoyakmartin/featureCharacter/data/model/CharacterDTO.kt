package ru.svoyakmartin.featureCharacter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureCharacter.domain.model.Gender
import ru.svoyakmartin.featureCharacter.domain.model.Status

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
    var episode: List<String>
)
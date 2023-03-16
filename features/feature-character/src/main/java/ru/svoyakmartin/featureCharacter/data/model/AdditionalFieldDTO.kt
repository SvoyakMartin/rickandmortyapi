package ru.svoyakmartin.featureCharacter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalFieldDTO(
    @SerialName("name")
    var name: String,
    @SerialName("url")
    var url: String
)
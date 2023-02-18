package ru.svoyakmartin.featureCharacter.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AdditionalFieldDTO(
    var name: String,
    var url: String
)
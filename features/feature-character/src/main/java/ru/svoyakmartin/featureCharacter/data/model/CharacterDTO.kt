package ru.svoyakmartin.featureCharacter.data.model

import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureCharacter.domain.model.Gender
import ru.svoyakmartin.featureCharacter.domain.model.Status

@Serializable
data class CharacterDTO(
    var id: Int,
    var name: String,
    var status: Status,
    var species: String,
    var type: String,
    var gender: Gender,
    var origin: AdditionalFieldDTO,
    var location: AdditionalFieldDTO,
    var image: String,
    var episode: List<String>
)
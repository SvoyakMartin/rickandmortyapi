package ru.svoyakmartin.featureCharacter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureCore.data.model.InfoDTO

@Serializable
data class CharactersDTO(
    @SerialName("info")
    var info: InfoDTO,
    @SerialName("results")
    var results: List<CharacterDTO>,
)
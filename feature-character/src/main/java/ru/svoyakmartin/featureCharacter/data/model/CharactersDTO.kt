package ru.svoyakmartin.featureCharacter.data.model

import kotlinx.serialization.Serializable
import ru.svoyakmartin.featureIinfo.InfoDTO

@Serializable
data class CharactersDTO(
    var info: InfoDTO,
    var results: List<CharacterDTO>,
)
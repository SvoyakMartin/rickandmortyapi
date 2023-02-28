package ru.svoyakmartin.featureCharacter.data.dataSource

import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCore.util.getIdFromUrl
import ru.svoyakmartin.featureCore.util.getIdsListFromUrlList

fun CharacterDTO.toCharacter(): Character {
    return Character(
        id,
        name,
        status,
        species,
        type,
        gender,
        if (origin.url.isEmpty()) null else getIdFromUrl(origin.url),
        if (location.url.isEmpty()) null else getIdFromUrl(location.url),
        image,
    )
}

fun CharacterDTO.getEpisodesIds(): Set<Int> {
    return getIdsListFromUrlList(episode)
}

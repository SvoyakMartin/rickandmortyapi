package ru.svoyakmartin.featureCharacter.data.dataSource

import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.domain.model.Character

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

fun CharacterDTO.getEpisodesIds(): List<Int> {
    return getIdsListFromUrlList(episode)
}

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

fun getIdsListFromUrlList(urlList: List<String>): List<Int> {
    val ids = arrayListOf<Int>()
    urlList.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}
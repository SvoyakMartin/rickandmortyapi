package ru.svoyakmartin.rickandmortyapi.data.repository

import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.remote.models.CharacterDTO
import ru.svoyakmartin.rickandmortyapi.data.remote.models.Root
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
        url,
        getTimeFromString(created)
    )
}

fun CharacterDTO.getEpisodesId(): List<Int> {
    val ids = arrayListOf<Int>()
    episode.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

fun getTimeFromString(timeStamp: String): Long {
    return LocalDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME)
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
}

fun Root.toMap(): Map<String, Int> {
    with(data.stats){
        return mapOf(
            "characters" to characters.info.count,
            "locations" to locations.info.count,
            "episodes" to episodes.info.count
        )
    }
}
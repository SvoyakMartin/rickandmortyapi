package ru.svoyakmartin.featureCharacter.data.dataSource

import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.domain.model.Character
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

fun getTimeFromString(timeStamp: String): Long {
    return LocalDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME)
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
}

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}
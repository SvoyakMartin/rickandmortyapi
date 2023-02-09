package ru.svoyakmartin.rickandmortyapi.data.repository

import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.data.remote.models.EpisodeDTO
import ru.svoyakmartin.rickandmortyapi.data.remote.models.LocationDTO
import ru.svoyakmartin.rickandmortyapi.data.remote.models.Root
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LocationDTO.toLocation(): Location {
    return Location(
        id,
        name,
        type,
        dimension,
        url,
        getTimeFromString(created)
    )
}

fun LocationDTO.getCharactersIds(): List<Int> {
    return getIdsListFromUrlList(residents)
}

fun ru.svoyakmartin.featureCharacter.data.model.CharacterDTO.getEpisodesIds(): List<Int> {
    return getIdsListFromUrlList(episode)
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
    with(data.stats) {
        return mapOf(
            "characters" to characters.info.count,
            "locations" to locations.info.count,
            "episodes" to episodes.info.count
        )
    }
}

fun EpisodeDTO.toEpisode(): Episode {
    return Episode(
        id,
        name,
        airDate,
        episode,
        url,
        getTimeFromString(created)
    )
}

fun EpisodeDTO.getCharactersIds(): List<Int> {
   return getIdsListFromUrlList(characters)
}

fun getIdsListFromUrlList(urlList: List<String>): List<Int> {
    val ids = arrayListOf<Int>()
    urlList.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}
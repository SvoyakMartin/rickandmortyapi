package ru.svoyakmartin.rickandmortyapi.data.repository

import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO
import ru.svoyakmartin.rickandmortyapi.data.remote.models.Root
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun ru.svoyakmartin.featureLocation.data.model.LocationDTO.getCharactersIds(): List<Int> {
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


fun ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO.getCharactersIds(): List<Int> {
   return getIdsListFromUrlList(characters)
}

fun getIdsListFromUrlList(urlList: List<String>): List<Int> {
    val ids = arrayListOf<Int>()
    urlList.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}
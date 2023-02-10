package ru.svoyakmartin.featureEpisode.data.dataSource

import ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

fun getTimeFromString(timeStamp: String): Long {
    return LocalDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME)
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
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
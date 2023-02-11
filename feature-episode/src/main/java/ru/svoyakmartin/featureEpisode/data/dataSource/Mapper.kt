package ru.svoyakmartin.featureEpisode.data.dataSource

import ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO
import ru.svoyakmartin.featureEpisode.domain.model.Episode

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

fun EpisodeDTO.toEpisode(): Episode {
    return Episode(
        id,
        name,
        airDate,
        episode,
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
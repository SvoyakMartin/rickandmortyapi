package ru.svoyakmartin.featureEpisode.data.dataSource

import ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureCore.util.getIdsListFromUrlList

fun EpisodeDTO.toEpisode(): Episode {
    return Episode(
        id,
        name,
        airDate,
        episode,
    )
}

fun EpisodeDTO.getCharactersIds(): Set<Int> {
   return getIdsListFromUrlList(characters)
}
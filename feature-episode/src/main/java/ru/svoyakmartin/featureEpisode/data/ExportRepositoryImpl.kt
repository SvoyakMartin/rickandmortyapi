package ru.svoyakmartin.featureEpisode.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureEpisode.data.dataSource.EpisodesApi
import ru.svoyakmartin.featureEpisode.data.dataSource.getCharactersIds
import ru.svoyakmartin.featureEpisode.data.dataSource.toEpisode
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import javax.inject.Inject

@AppScope
class ExportRepositoryImpl @Inject constructor(
    private val episodeRoomDAO: EpisodeRoomDAO,
    private val apiService: EpisodesApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
) {

    fun getEpisodeMapByIds(episodeIdsList: List<Int>) = flow {
        episodeRoomDAO.getExistingEpisodeIds(episodeIdsList)
            .collect { existingEpisodeIdsList ->
                val difference = episodeIdsList.minus(existingEpisodeIdsList)

                if (difference.isNotEmpty()) {
                    fetchEpisodesByIds(difference.joinToString())
                }

                episodeRoomDAO.getEpisodesNameByIds(episodeIdsList).collect {emit(it) }
            }
    }

    private suspend fun fetchEpisodesByIds(ids: String) {
        val response = apiService.getEpisodesByIds(ids)
        response.body()?.apply {
            val episodesList = ArrayList<Episode>()
            val episodesAndCharactersIdsMap = mutableMapOf<Int, List<Int>>()

            forEach { episodeDTO ->
                val episode = episodeDTO.toEpisode()
                episodesList.add(episode)
                episodesAndCharactersIdsMap[episodeDTO.id] = episodeDTO.getCharactersIds()
            }

            episodeRoomDAO.insertEpisodes(episodesList)
            characterDependenciesFeatureApi.insertEpisodesAndCharacters(episodesAndCharactersIdsMap)
        }
    }
}
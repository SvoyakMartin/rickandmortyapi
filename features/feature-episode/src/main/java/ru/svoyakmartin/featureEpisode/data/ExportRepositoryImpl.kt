package ru.svoyakmartin.featureEpisode.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
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

    fun getEpisodeMapByIds(episodeIdsList: Set<Int>) = flow {
        episodeRoomDAO.getExistingEpisodeIds(episodeIdsList)
            .collect { existingEpisodeIdsList ->
                val difference = episodeIdsList.minus(existingEpisodeIdsList.toSet())

                if (difference.isNotEmpty()) {
                    fetchEpisodesByIds(difference.joinToString()).collect { response ->
                        emit(response)
                    }
                }

                episodeRoomDAO.getEpisodesNameByIds(episodeIdsList).collect { emit(it) }
            }
    }

    private suspend fun fetchEpisodesByIds(ids: String) = flow {
        when (val response = apiService.getEpisodesByIds(ids)) {
            is ApiResponse.Success -> {
                val episodesList = ArrayList<Episode>()
                val episodesAndCharactersIdsMap = mutableMapOf<Int, Set<Int>>()

                response.data.forEach { episodeDTO ->
                    val episode = episodeDTO.toEpisode()
                    episodesList.add(episode)
                    episodesAndCharactersIdsMap[episodeDTO.id] = episodeDTO.getCharactersIds()
                }

                episodeRoomDAO.insertEpisodes(episodesList)
                characterDependenciesFeatureApi.insertEpisodesAndCharacters(
                    episodesAndCharactersIdsMap
                )
            }
            else -> {
                emit(response)
            }
        }
    }
}
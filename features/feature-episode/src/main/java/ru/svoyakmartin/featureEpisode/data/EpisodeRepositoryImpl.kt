package ru.svoyakmartin.featureEpisode.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureEpisode.data.dataSource.EpisodesApi
import ru.svoyakmartin.featureEpisode.data.dataSource.getCharactersIds
import ru.svoyakmartin.featureEpisode.data.dataSource.toEpisode
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val episodeRoomDAO: EpisodeRoomDAO,
    private val settings: SettingsFeatureApi,
    private val apiService: EpisodesApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi,
    private val characterFeatureApi: CharacterFeatureApi,
    statisticFeatureApi: StatisticFeatureApi
) {
    val allEpisodes = episodeRoomDAO.getAllEpisodes()
    val episodesCount = statisticFeatureApi.getEpisodesCount()
    private var episodesLastPage = settings.readInt(SettingsFeatureApi.EPISODES_LAST_PAGE_KEY, 1)

    suspend fun fetchNextEpisodesPartFromWeb() = flow {
        val response = apiService.getEpisodes(episodesLastPage)

        if (response is ApiResponse.Success) {
            val episodesList = ArrayList<Episode>()
            val episodesAndCharactersIdsMap = mutableMapOf<Int, Set<Int>>()

            response.data.apply {
                results.forEach { episodesDTO ->
                    val episode = episodesDTO.toEpisode()
                    episodesList.add(episode)
                    episodesAndCharactersIdsMap[episode.id] = episodesDTO.getCharactersIds()
                }

                episodeRoomDAO.insertEpisodes(episodesList)
                characterDependenciesFeatureApi.insertEpisodesAndCharacters(
                    episodesAndCharactersIdsMap
                )

                if (info.pages > episodesLastPage) {
                    settings.saveInt(SettingsFeatureApi.EPISODES_LAST_PAGE_KEY, ++episodesLastPage)
                }
            }

            emit(true)
        } else {
            emit(response)
        }
    }

    suspend fun getEpisodeById(id: Int) = flow {
        episodeRoomDAO.getEpisodeById(id).collect { episode ->
            if (episode != null) {
                emit(episode)
            } else {
                fetchEpisodeById(id).collect {
                    emit(it)
                }
            }
        }
    }

    private suspend fun fetchEpisodeById(id: Int) = flow {
        val response = apiService.getEpisodeById(id)

        if (response is ApiResponse.Success) {
            episodeRoomDAO.insertEpisode(response.data.toEpisode())
        }

        emit(response)
    }

    suspend fun getCharacterMapByEpisodeId(episodeId: Int) = flow {
        characterDependenciesFeatureApi.getCharactersIdsByEpisodeId(episodeId)
            .collect { characterIdsList ->
                characterFeatureApi.getCharacterMapByIds(characterIdsList).collect {
                    emit(it)
                }
            }
    }
}
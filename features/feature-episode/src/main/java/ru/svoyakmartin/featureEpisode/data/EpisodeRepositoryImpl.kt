package ru.svoyakmartin.featureEpisode.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureEpisode.data.dataSource.EpisodesApi
import ru.svoyakmartin.featureEpisode.data.dataSource.getCharactersIds
import ru.svoyakmartin.featureEpisode.data.dataSource.toEpisode
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val episodeRoomDAO: EpisodeRoomDAO,
    private val settings: SettingsFeatureApi,
    private val apiService: EpisodesApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi,
    private val characterFeatureApi: CharacterFeatureApi
) {
    val allEpisodes = episodeRoomDAO.getAllEpisodes()
    private var episodesLastPage = settings.readInt(SettingsFeatureApi.EPISODES_LAST_PAGE_KEY, 1)

    suspend fun fetchNextEpisodesPartFromWeb() {
        val response = apiService.getEpisodes(episodesLastPage)
        response.body()?.apply {
            val episodesList = ArrayList<Episode>()
            val episodesAndCharactersIdsMap = mutableMapOf<Int, List<Int>>()

            results.forEach { episodesDTO ->
                val episode = episodesDTO.toEpisode()
                episodesList.add(episode)
                episodesAndCharactersIdsMap[episode.id] = episodesDTO.getCharactersIds()
            }

            episodeRoomDAO.insertEpisodes(episodesList)
            characterDependenciesFeatureApi.insertEpisodesAndCharacters(episodesAndCharactersIdsMap)

            if (info.pages > episodesLastPage) {
                settings.saveInt(SettingsFeatureApi.EPISODES_LAST_PAGE_KEY, ++episodesLastPage)
            }
        }
    }

    suspend fun getEpisodeById(id: Int) = flow {
        episodeRoomDAO.getEpisodeById(id).collect { character ->
            if (character != null) {
                emit(character)
            } else {
                fetchEpisodeById(id).collect {
                    emit(it)
                }
            }
        }
    }

    private suspend fun fetchEpisodeById(id: Int) = flow {
        val response = apiService.getEpisodeById(id)

        response.body()?.apply {
            val character = toEpisode()
            episodeRoomDAO.insertEpisode(character)

            emit(character)
        }
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
package ru.svoyakmartin.featureEpisode.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
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
    private val characterFeatureApi: CharacterFeatureApi
) {
    val allEpisodes = episodeRoomDAO.getAllEpisodes()
    private var episodesLastPage = settings.readInt(SettingsFeatureApi.EPISODES_LAST_PAGE_KEY, 1)

    suspend fun fetchNextEpisodesPartFromWeb() {
        val response = apiService.getEpisodes(episodesLastPage)
        response.body()?.apply {
            val episodesList = ArrayList<Episode>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()

            results.forEach { episodesDTO ->
                val episode = episodesDTO.toEpisode()
                episodesList.add(episode)
                charactersAndEpisodesIdsMap[episode.id] = episodesDTO.getCharactersIds()
            }

            episodeRoomDAO.insertEpisodes(episodesList)
            characterFeatureApi.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)

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

    suspend fun getCharacterMapByEpisodeId(episodeId: Int) = flow{
            characterFeatureApi.getCharacterMapByEpisodeId(episodeId).collect { emit(it) }
    }
//    private suspend fun fetchEpisodesByIds(ids: String) {
//        val response = apiService.getEpisodesByIds(ids)
//        response.body()?.apply {
//            val episodesList = ArrayList<ru.svoyakmartin.featureEpisode.domain.model.Episode>()
//            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()
//
//            forEach { episodeDTO ->
//                val episode = episodeDTO.toEpisode()
//                episodesList.add(episode)
//                charactersAndEpisodesIdsMap[episodeDTO.id] = episodeDTO.getCharactersIds()
//            }
//
//            roomDAO.insertEpisodesAndDependencies(episodesList, charactersAndEpisodesIdsMap)
//        }
//    }


//    fun getEpisodesByCharacterId(characterId: Int): Flow<List<ru.svoyakmartin.featureEpisode.domain.model.Episode>?> {
//        return flow {
//            roomDAO.getMissingEpisodeIdsByCharacterId(characterId)
//                .collect { episodeIdsList ->
//                    if (!episodeIdsList.isNullOrEmpty()) {
//                        fetchEpisodesByIds(episodeIdsList.joinToString())
//                    }
//
//                    roomDAO.getEpisodesByCharacterId(characterId).collect { episodesList ->
//                        emit(episodesList)
//                    }
//                }
//        }
//    }

//    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>?> {
//        return flow {
//            roomDAO.getMissingCharacterIdsByEpisodeId(episodeId)
//                .collect { characterIdsList ->
//                    if (!characterIdsList.isNullOrEmpty()) {
//                        fetchCharactersByIds(characterIdsList.joinToString())
//                    }
//
//                    roomDAO.getCharactersByEpisodeId(episodeId).collect { episodesList ->
//                        emit(episodesList)
//                    }
//                }
//        }
//    }
}
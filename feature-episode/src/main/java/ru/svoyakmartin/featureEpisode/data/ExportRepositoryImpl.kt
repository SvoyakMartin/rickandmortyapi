package ru.svoyakmartin.featureEpisode.data

import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureEpisode.data.dataSource.EpisodesApi
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO
import javax.inject.Inject

@AppScope
class ExportRepositoryImpl @Inject constructor(
    private val episodeRoomDAO: EpisodeRoomDAO,
    private val apiService: EpisodesApi
) {

//    suspend fun fetchNextEpisodesPartFromWeb() {
//        val response = apiService.getEpisodes(episodesLastPage)
//        response.body()?.apply {
//            val episodesList = ArrayList<Episode>()
//            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()
//
//            results.forEach { episodesDTO ->
//                val episode = episodesDTO.toEpisode()
//                episodesList.add(episode)
//                charactersAndEpisodesIdsMap[episode.id] = episodesDTO.getCharactersIds()
//            }
//
//            if (info.pages > episodesLastPage) {
//                settings.saveEpisodesLastPage(++episodesLastPage)
//            }
//
//            roomDAO.insertEpisodesAndDependencies(episodesList, charactersAndEpisodesIdsMap)
//        }
//    }

//    private suspend fun fetchEpisodesByIds(ids: String) {
//        val response = apiService.getEpisodesByIds(ids)
//        response.body()?.apply {
//            val episodesList = ArrayList<Episode>()
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


//    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>?> {
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

//    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<Character>?> {
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
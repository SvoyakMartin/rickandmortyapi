package ru.svoyakmartin.featureEpisode.data

import ru.svoyakmartin.coreDi.di.scope.AppScope
import javax.inject.Inject

@AppScope
class EpisodeRepositoryImpl @Inject constructor(
    private val episodeRoomDAO: ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO,
//    private val settings: UserPreferencesRepositoryImpl,
//    private val apiService: ru.svoyakmartin.featureEpisode.data.dataSource.ApiService
) {
    val allEpisodes = episodeRoomDAO.getAllEpisodes()
//    private var episodesLastPage = settings.readSavedEpisodesLastPage()
//    private lateinit var statistic: Map<String, Int>

//    suspend fun getStatistic() {
//        apiService.getStatistic().body()?.let {
//            statistic = it.toMap()
//        }
//    }

    suspend fun fetchNextCharactersPartFromWeb() {
//        val response = apiService.getCharacters(charactersLastPage)
//        response.body()?.apply {
//            val charactersList = ArrayList<ru.svoyakmartin.featureCharacter.domain.model.Character>()
//            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()
//
//            results.forEach { characterDTO ->
//                val character = characterDTO.toCharacter()
//                charactersList.add(character)
//                charactersAndEpisodesIdsMap[character.id] = characterDTO.getEpisodesIds()
//            }
//
//            if (info.pages > charactersLastPage) {
//                settings.saveCharactersLastPage(++charactersLastPage)
//            }
//
//            roomDAO.insertCharactersAndDependencies(charactersList, charactersAndEpisodesIdsMap)
//        }
    }

//    suspend fun fetchNextEpisodesPartFromWeb() {
//        val response = apiService.getEpisodes(episodesLastPage)
//        response.body()?.apply {
//            val episodesList = ArrayList<ru.svoyakmartin.featureEpisode.domain.model.Episode>()
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



    private suspend fun fetchCharactersByIds(ids: String) {
//        val response = apiService.getCharactersById(ids)
//        response.body()?.apply {
//            val charactersList = ArrayList<ru.svoyakmartin.featureCharacter.domain.model.Character>()
//            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()
//
//            forEach { characterDTO ->
//                val character = characterDTO.toCharacter()
//                charactersList.add(character)
//                charactersAndEpisodesIdsMap[characterDTO.id] = characterDTO.getEpisodesIds()
//            }
//
//            roomDAO.insertCharactersAndDependencies(charactersList, charactersAndEpisodesIdsMap)
//        }
    }

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

//    fun getCharactersByLocationId(locationId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>?> {
//        return flow {
//            roomDAO.getMissingCharacterIdsByLocationId(locationId)
//                .collect { characterIdsList ->
//                    if (!characterIdsList.isNullOrEmpty()) {
//                        fetchCharactersByIds(characterIdsList.joinToString())
//                    }
//
//                    roomDAO.getCharactersByLocationId(locationId).collect { locationsList ->
//                        emit(locationsList)
//                    }
//                }
//        }
//    }
}
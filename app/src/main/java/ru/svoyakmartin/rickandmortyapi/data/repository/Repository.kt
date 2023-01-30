package ru.svoyakmartin.rickandmortyapi.data.repository

import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.ApiService
import ru.svoyakmartin.rickandmortyapi.di.annotations.AppScope
import javax.inject.Inject

@AppScope
class Repository @Inject constructor(
    private val roomDAO: RoomDAO,
    private val settings: UserPreferencesRepository,
    private val apiService: ApiService
) {
    val allCharacters = roomDAO.getAllCharacters()
//    val allLocations = roomDAO.getAllLocations()
//    val allEpisodes = roomDAO.getAllEpisodes()
    private var charactersLastPage = settings.readSavedCharactersLastPage()
//    private lateinit var statistic: Map<String, Int>

//    suspend fun getStatistic() {
//        apiService.getStatistic().body()?.let {
//            statistic = it.toMap()
//        }
//    }

    suspend fun fetchNextCharactersPartFromWeb() {
        val response = apiService.getCharacters(charactersLastPage)
        response.body()?.apply {
            val charactersList = ArrayList<Character>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()

            results.forEach { characterDTO ->
                val character = characterDTO.toCharacter()
                charactersList.add(character)
                charactersAndEpisodesIdsMap[character.id] = characterDTO.getEpisodesIds()
            }

            if (info.pages > charactersLastPage) {
                settings.saveCharactersLastPage(++charactersLastPage)
            }

            roomDAO.insertCharactersAndDependencies(charactersList, charactersAndEpisodesIdsMap)
        }
    }

    fun getLocationById(id: Int): Flow<Location?> {
        return roomDAO.getLocationById(id)
    }

    private fun getMissingEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>?> {
        return roomDAO.getMissingEpisodeIdsByCharacterId(characterId)
    }

    private suspend fun insertEpisodesAndDependencies(
        episodesList: List<Episode>,
        charactersAndEpisodesIdsMap: Map<Int, List<Int>>
    ) {
        roomDAO.insertEpisodesAndDependencies(episodesList, charactersAndEpisodesIdsMap)
    }

    private suspend fun fetchEpisodesByIds(ids: String) {
        val response = apiService.getEpisodesByIds(ids)
        response.body()?.apply {
            val episodesList = ArrayList<Episode>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()

            forEach { episodeDTO ->
                val episode = episodeDTO.toEpisode()
                episodesList.add(episode)
                charactersAndEpisodesIdsMap[episodeDTO.id] = episodeDTO.getCharactersIds()
            }

            insertEpisodesAndDependencies(episodesList, charactersAndEpisodesIdsMap)
        }
    }

    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>?> {
        return flow {
            getMissingEpisodeIdsByCharacterId(characterId)
                .collect { episodeIdsList ->
                    if (!episodeIdsList.isNullOrEmpty()) {
                        fetchEpisodesByIds(episodeIdsList.joinToString())
                    }

                    roomDAO.getEpisodesByCharactersId(characterId).collect { episodesList ->
                        emit(episodesList)
                    }
                }
        }
    }
}
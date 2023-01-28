package ru.svoyakmartin.rickandmortyapi.data.repository

import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val roomDAO: RoomDAO) {
    val allCharacters = roomDAO.getAllCharacters()
    val allLocations = roomDAO.getAllLocations()
    val allEpisodes = roomDAO.getAllEpisodes()
    private val settings = UserPreferencesRepository.getInstance()!!
    private var lastPage = settings.readSavedLastPage()
    @Inject
    lateinit var apiService: ApiService
    private lateinit var statistic: Map<String, Int>

    suspend fun getStatistic() {
        apiService.getStatistic().body()?.let {
            statistic = it.toMap()
        }
    }

    suspend fun getCharactersPartFromWeb() {
        val response = apiService.getCharacters(lastPage)
        response.body()?.apply {
            val characters = ArrayList<Character>()
            val charactersEpisodes = mutableMapOf<Int, List<Int>>()

            results.forEach {
                val character = it.toCharacter()
                characters.add(character)
                charactersEpisodes[character.id] = it.getEpisodesIds()
            }

            if (info.pages > lastPage) {
                settings.saveLastPage(++lastPage)
            }

            roomDAO.insertCharactersAndDependencies(characters, charactersEpisodes)
        }

    }

    fun getLocation(id: Int): Flow<Location?> {
        return roomDAO.getLocation(id)
    }

    fun getMissingEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>?> {
        return roomDAO.getMissingEpisodeIdsByCharacterId(characterId)
    }

    suspend fun insertEpisodesAndDependencies(
        episodes: List<Episode>,
        charactersEpisodes: Map<Int, List<Int>>
    ) {
        roomDAO.insertEpisodesAndDependencies(episodes, charactersEpisodes)
    }

    suspend fun getEpisodesByIds(ids: String) {
        val response = apiService.getEpisodesByIds(ids)
        response.body()?.apply {
            val episodes = ArrayList<Episode>()
            val charactersEpisodes = mutableMapOf<Int, List<Int>>()

            forEach {
                val episode = it.toEpisode()
                episodes.add(episode)
                charactersEpisodes[it.id] = it.getCharactersIds()
            }

            insertEpisodesAndDependencies(episodes, charactersEpisodes)
        }
    }

    fun getEpisodesByCharactersId(characterId: Int): Flow<List<Episode>?> {
        return flow {
            getMissingEpisodeIdsByCharacterId(characterId)
                .collect {
                    if (!it.isNullOrEmpty()) {
                        getEpisodesByIds(it.joinToString())
                    }

                    roomDAO.getEpisodesByCharactersId(characterId).collect {
                        emit(it)
                    }
                }
        }
    }
}
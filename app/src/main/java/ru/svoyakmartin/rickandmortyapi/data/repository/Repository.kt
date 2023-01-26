package ru.svoyakmartin.rickandmortyapi.data.repository

import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.RickAndMortyApi

class Repository(private val roomDAO: RoomDAO) {
    val allCharacters = roomDAO.getAllCharacters()
    val allLocations = roomDAO.getAllLocations()
    val allEpisodes = roomDAO.getAllEpisodes()
    private val settings = UserPreferencesRepository.getInstance()!!
    private var lastPage = settings.readSavedLastPage()
    private val retrofit = RickAndMortyApi.retrofitService
    private lateinit var statistic: Map<String, Int>

    suspend fun getStatistic() {
        retrofit.getStatistic().body()?.let {
            statistic = it.toMap()
        }
    }

    suspend fun getCharacters() {
        val response = retrofit.getCharacters(lastPage)
        response.body()?.apply {
            val characters = ArrayList<Character>()
            val charactersEpisodes = mutableMapOf<Int, List<Int>>()

            results.forEach {
                val character = it.toCharacter()
                characters.add(character)
                charactersEpisodes[character.id] = it.getEpisodesId()
            }

            if (info.pages > lastPage) {
                settings.saveLastPage(++lastPage)
            }

            roomDAO.insertCharactersAndDependencies(characters, charactersEpisodes)
        }

    }

    private suspend fun insertCharactersAndDependencies() {
//        if (episodesId.size == 1) {
//            val response = retrofit.getEpisode(episodesId.first())
//        } else {
//            val ids = episodesId.joinToString(",", transform = Int::toString)
//            val response = retrofit.getEpisodesById(ids)
//            response.body()?.apply {
//
//            }
//        }
    }

    fun getLocation(id: Int): Flow<Location?> {
        return roomDAO.getLocation(id)
    }

    fun getEpisodesByCharactersId(id: Int): Flow<List<Episode>?> {
        return flow {
            roomDAO.getEpisodesByCharactersId(id)
                .collect {
                    if (it != null){
                        emit(it)
                    }

                }
        }

    }
}
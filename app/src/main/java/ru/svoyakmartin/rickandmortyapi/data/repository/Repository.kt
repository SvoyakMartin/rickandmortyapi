package ru.svoyakmartin.rickandmortyapi.data.repository

import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.ApiService
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl
import javax.inject.Inject

@AppScope
class Repository @Inject constructor(
    private val roomDAO: RoomDAO,
    private val settings: UserPreferencesRepositoryImpl,
    private val apiService: ApiService
) {
    val allLocations = roomDAO.getAllLocations()
    val allEpisodes = roomDAO.getAllEpisodes()
    private var episodesLastPage = settings.readSavedEpisodesLastPage()
    private var locationsLastPage = settings.readSavedLocationsLastPage()
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

            if (info.pages > episodesLastPage) {
                settings.saveEpisodesLastPage(++episodesLastPage)
            }

            roomDAO.insertEpisodesAndDependencies(episodesList, charactersAndEpisodesIdsMap)
        }
    }

    suspend fun fetchNextLocationsPartFromWeb() {
        val response = apiService.getLocations(locationsLastPage)
        response.body()?.apply {
            val locationsList = ArrayList<Location>()
            val charactersAndLocationsIdsMap = mutableMapOf<Int, List<Int>>()

            results.forEach { locationsDTO ->
                val location = locationsDTO.toLocation()
                locationsList.add(location)
                charactersAndLocationsIdsMap[location.id] = locationsDTO.getCharactersIds()
            }

            if (info.pages > locationsLastPage) {
                settings.saveLocationsLastPage(++locationsLastPage)
            }

            roomDAO.insertLocationsAndDependencies(locationsList, charactersAndLocationsIdsMap)
        }
    }

    fun getLocationById(locationId: Int): Flow<Location?> {
        return flow {
            roomDAO.getLocationById(locationId)
                .collect { location ->

                    if (location != null) {
                        emit(location)
                    } else {
                        emit(fetchLocationById(locationId.toString()))
                    }
                }
        }
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

            roomDAO.insertEpisodesAndDependencies(episodesList, charactersAndEpisodesIdsMap)
        }
    }

    private suspend fun fetchLocationById(id: String): Location? {
        val response = apiService.getLocationById(id)
        response.body()?.apply {
            val location = this.toLocation()

            val charactersAndLocationsIdsMap = mutableMapOf<Int, List<Int>>()

            charactersAndLocationsIdsMap[location.id] = this.getCharactersIds()

            roomDAO.insertLocationsAndDependencies(
                arrayListOf(location),
                charactersAndLocationsIdsMap
            )

            return location
        }
        return null
    }


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

    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>?> {
        return flow {
            roomDAO.getMissingEpisodeIdsByCharacterId(characterId)
                .collect { episodeIdsList ->
                    if (!episodeIdsList.isNullOrEmpty()) {
                        fetchEpisodesByIds(episodeIdsList.joinToString())
                    }

                    roomDAO.getEpisodesByCharacterId(characterId).collect { episodesList ->
                        emit(episodesList)
                    }
                }
        }
    }

    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>?> {
        return flow {
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
        }
    }

    fun getCharactersByLocationId(locationId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>?> {
        return flow {
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
        }
    }
}
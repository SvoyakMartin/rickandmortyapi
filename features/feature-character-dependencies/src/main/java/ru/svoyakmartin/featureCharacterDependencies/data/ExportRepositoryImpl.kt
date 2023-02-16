package ru.svoyakmartin.featureCharacterDependencies.data

import ru.svoyakmartin.featureCharacterDependencies.data.db.CharacterDependenciesRoomDAO
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndEpisodes
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndLocations
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(private val characterDependenciesRoomDAO: CharacterDependenciesRoomDAO) {

    fun getCharactersIdsByEpisodeId(episodeId: Int) =
        characterDependenciesRoomDAO.getCharactersIdsByEpisodeId(episodeId)

    fun getCharactersIdsByLocationId(locationsId: Int) =
        characterDependenciesRoomDAO.getCharactersIdsByLocationId(locationsId)

    fun getEpisodesIdsByCharacterId(characterId: Int) =
        characterDependenciesRoomDAO.getEpisodesIdsByCharacterId(characterId)

    suspend fun insertLocationsAndCharacters(locationsAndCharactersIdsMap: Map<Int, List<Int>>) {
        val charactersAndLocationsList = arrayListOf<CharactersAndLocations>()

        locationsAndCharactersIdsMap.forEach { entry ->
            val locationId = entry.key

            entry.value.forEach { characterId ->
                charactersAndLocationsList.add(CharactersAndLocations(characterId, locationId))
            }
        }
        characterDependenciesRoomDAO.insertCharactersAndLocations(charactersAndLocationsList)
    }

    suspend fun insertEpisodesAndCharacters(episodesAndCharactersIdsMap: Map<Int, List<Int>>) {
        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()

        episodesAndCharactersIdsMap.forEach { entry ->
            val episodesId = entry.key

            entry.value.forEach { characterId ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterId, episodesId))
            }
        }
        characterDependenciesRoomDAO.insertCharactersAndEpisodes(charactersAndEpisodesList)
    }

    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, List<Int>>) {
        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersAndEpisodesIdsMap.forEach { entry ->
            val characterId = entry.key

            entry.value.forEach { episodesId ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterId, episodesId))
            }
        }
        characterDependenciesRoomDAO.insertCharactersAndEpisodes(charactersAndEpisodesList)
    }
}
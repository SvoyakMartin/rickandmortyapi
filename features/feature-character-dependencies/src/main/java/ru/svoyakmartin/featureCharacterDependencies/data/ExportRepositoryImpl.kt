package ru.svoyakmartin.featureCharacterDependencies.data

import kotlinx.coroutines.flow.map
import ru.svoyakmartin.featureCharacterDependencies.data.db.CharacterDependenciesRoomDAO
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndEpisodes
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndLocations
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(private val characterDependenciesRoomDAO: CharacterDependenciesRoomDAO) {

    fun getCharactersIdsByEpisodeId(episodeId: Int) =
        characterDependenciesRoomDAO.getCharactersIdsByEpisodeId(episodeId).map { it.toSet() }

    fun getCharactersIdsByLocationId(locationsId: Int) =
        characterDependenciesRoomDAO.getCharactersIdsByLocationId(locationsId).map { it.toSet() }

    fun getEpisodesIdsByCharacterId(characterId: Int) =
        characterDependenciesRoomDAO.getEpisodesIdsByCharacterId(characterId).map { it.toSet() }

    suspend fun insertLocationsAndCharacters(locationsAndCharactersIdsMap: Map<Int, Set<Int>>) {
        val charactersAndLocationsList = mutableSetOf<CharactersAndLocations>()

        locationsAndCharactersIdsMap.forEach { entry ->
            val locationId = entry.key

            entry.value.forEach { characterId ->
                charactersAndLocationsList.add(CharactersAndLocations(characterId, locationId))
            }
        }
        characterDependenciesRoomDAO.insertCharactersAndLocations(charactersAndLocationsList)
    }

    suspend fun insertEpisodesAndCharacters(episodesAndCharactersIdsMap: Map<Int, Set<Int>>) {
        val charactersAndEpisodesList = mutableSetOf<CharactersAndEpisodes>()

        episodesAndCharactersIdsMap.forEach { entry ->
            val episodesId = entry.key

            entry.value.forEach { characterId ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterId, episodesId))
            }
        }
        characterDependenciesRoomDAO.insertCharactersAndEpisodes(charactersAndEpisodesList)
    }

    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, Set<Int>>) {
        val charactersAndEpisodesList = mutableSetOf<CharactersAndEpisodes>()

        charactersAndEpisodesIdsMap.forEach { entry ->
            val characterId = entry.key

            entry.value.forEach { episodesId ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterId, episodesId))
            }
        }
        characterDependenciesRoomDAO.insertCharactersAndEpisodes(charactersAndEpisodesList)
    }
}
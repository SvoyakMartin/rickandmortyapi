package ru.svoyakmartin.featureCharacter.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.dataSource.getEpisodesIds
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCharacter.domain.model.CharactersAndEpisodes
import ru.svoyakmartin.featureCharacter.domain.model.CharactersAndLocations
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val characterRoomDAO: CharacterRoomDAO,
    private val apiService: CharactersApi
) {

    private suspend fun fetchCharactersByIds(ids: String) {
        val response = apiService.getCharactersByIds(ids)

        response.body()?.apply {
            val charactersList = ArrayList<Character>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()

            forEach { characterDTO ->
                val character = characterDTO.toCharacter()
                charactersList.add(character)
                charactersAndEpisodesIdsMap[characterDTO.id] = characterDTO.getEpisodesIds()
            }

            characterRoomDAO.insertCharactersAndDependencies(
                charactersList,
                charactersAndEpisodesIdsMap
            )
        }
    }

    fun getCharacterMapByLocationId(locationId: Int) = flow {
        characterRoomDAO.getMissingCharacterIdsByLocationId(locationId)
            .collect { characterIdsList ->
                if (!characterIdsList.isNullOrEmpty()) {
                    fetchCharactersByIds(characterIdsList.joinToString())
                }

                characterRoomDAO.getCharactersByLocationId(locationId).collect {listCharacterDependencies ->
                    val characterNameMap = mutableMapOf<String, Int>()
                    listCharacterDependencies?.forEach {
                        characterNameMap[it.name] = it.id
                    }
                    emit(characterNameMap)
                }
            }
    }

    fun getCharacterMapByEpisodeId(episodeId: Int) = flow {
        characterRoomDAO.getMissingCharacterIdsByEpisodeId(episodeId)
            .collect { characterIdsList ->
                if (!characterIdsList.isNullOrEmpty()) {
                    fetchCharactersByIds(characterIdsList.joinToString())
                }

                characterRoomDAO.getCharactersByEpisodeId(episodeId).collect {listCharacterDependencies ->
                    val characterNameMap = mutableMapOf<String, Int>()
                    listCharacterDependencies?.forEach {
                        characterNameMap[it.name] = it.id
                    }
                    emit(characterNameMap)
                }
            }
    }

    suspend fun insertCharactersAndLocations(charactersAndLocationsIdsMap: Map<Int, List<Int>>){
        val charactersAndLocationsList = arrayListOf<CharactersAndLocations>()

        charactersAndLocationsIdsMap.forEach { (locationID, charactersIds) ->
            charactersIds.forEach { characterID ->
                charactersAndLocationsList.add(CharactersAndLocations(characterID, locationID))
            }
        }

        characterRoomDAO.insertCharactersAndLocations(charactersAndLocationsList)
    }

    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, List<Int>>){
        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersAndEpisodesIdsMap.forEach { (episodeID, charactersIds) ->
            charactersIds.forEach { characterID ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterID, episodeID))
            }
        }

        characterRoomDAO.insertCharactersAndEpisodes(charactersAndEpisodesList)
    }
}
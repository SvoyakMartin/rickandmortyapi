package ru.svoyakmartin.featureCharacter.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.dataSource.getEpisodesIds
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val characterRoomDAO: CharacterRoomDAO,
    private val apiService: CharactersApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
) {

    fun getCharacterMapByIds(characterIdsList: Set<Int>) = flow {
        characterRoomDAO.getExistingCharacterIds(characterIdsList)
            .collect { existingCharacterIdsList ->
                val difference = characterIdsList.minus(existingCharacterIdsList.toSet())

                if (difference.isNotEmpty()) {
                    fetchCharactersByIds(difference.joinToString()).collect { response ->
                        emit(response)
                    }
                }

                characterRoomDAO.getCharactersNameByIds(characterIdsList).collect { emit(it) }
            }
    }

    private suspend fun fetchCharactersByIds(ids: String) = flow {
        when (val response = apiService.getCharactersByIds(ids)) {
            is ApiResponse.Success -> {
                val charactersList = ArrayList<Character>()
                val charactersAndEpisodesIdsMap = mutableMapOf<Int, Set<Int>>()

                response.data.forEach { characterDTO ->
                    val character = characterDTO.toCharacter()
                    charactersList.add(character)
                    charactersAndEpisodesIdsMap[characterDTO.id] = characterDTO.getEpisodesIds()
                }

                characterRoomDAO.insertCharacters(charactersList)
                characterDependenciesFeatureApi.insertCharactersAndEpisodes(
                    charactersAndEpisodesIdsMap
                )
            }
            else -> {
                emit(response)
            }
        }
    }
}
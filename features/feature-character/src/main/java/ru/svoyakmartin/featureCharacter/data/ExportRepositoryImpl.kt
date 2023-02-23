package ru.svoyakmartin.featureCharacter.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    fun getCharacterMapByIds(characterIdsList: List<Int>) = flow {
        characterRoomDAO.getExistingCharacterIds(characterIdsList)
            .collect { existingCharacterIdsList ->
                val difference = characterIdsList.minus(existingCharacterIdsList)

                if (difference.isNotEmpty()) {
                    fetchCharactersByIds(difference.joinToString())
                }

                characterRoomDAO.getCharactersNameByIds(characterIdsList).collect { emit(it) }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun fetchCharactersByIds(ids: String) {
        val response = apiService.getCharactersByIds(ids)
//        if (response is ApiResponse.Success) {
//            response.data.apply {
//            val charactersList = ArrayList<Character>()
//            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()
//
//            forEach { characterDTO ->
//                val character = characterDTO.toCharacter()
//                charactersList.add(character)
//                charactersAndEpisodesIdsMap[characterDTO.id] = characterDTO.getEpisodesIds()
//            }
//
//            characterRoomDAO.insertCharacters(charactersList)
//            characterDependenciesFeatureApi.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)
//            }
//        }

        response.body()?.apply {
            val charactersList = ArrayList<Character>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()

            forEach { characterDTO ->
                val character = characterDTO.toCharacter()
                charactersList.add(character)
                charactersAndEpisodesIdsMap[characterDTO.id] = characterDTO.getEpisodesIds()
            }

            characterRoomDAO.insertCharacters(charactersList)
            characterDependenciesFeatureApi.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)
        }
    }
}
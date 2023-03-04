package ru.svoyakmartin.featureCharacter.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureCharacter.ERROR_FIELD
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.dataSource.getEpisodesIds
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.random.Random

class ExportRepositoryImpl @Inject constructor(
    private val characterRoomDAO: CharacterRoomDAO,
    private val apiService: CharactersApi,
    private val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi,
    statisticFeatureApi: StatisticFeatureApi
) : BaseRepository(characterRoomDAO, apiService, statisticFeatureApi) {

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

    fun getRandomCharacterMap(): Flow<Map<String, Any>?> = flow {
        charactersCount.collect { countOrError ->
            if (countOrError is Int) {
                val random = Random(Date().time)
                getCharacterById(random.nextInt(countOrError))
                    .collect { response ->
                        emit(
                            when (response) {
                                is Character -> response.toMap()
                                is ApiResponse.Success<*> ->
                                    (response.data as CharacterDTO).toCharacter().toMap()
                                is ApiResponse.Failure -> mapOf(ERROR_FIELD to response)
                                else -> null
                            }
                        )
                    }
            } else {
                emit(mapOf(ERROR_FIELD to countOrError))
            }
        }
    }.flowOn(Dispatchers.IO)
}
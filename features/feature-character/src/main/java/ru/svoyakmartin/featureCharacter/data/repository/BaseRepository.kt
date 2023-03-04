package ru.svoyakmartin.featureCharacter.data.repository

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    private val characterRoomDAO: CharacterRoomDAO,
    private val apiService: CharactersApi,
    statisticFeatureApi: StatisticFeatureApi
) {
    val charactersCount = statisticFeatureApi.getCharactersCount()

    suspend fun getCharacterById(id: Int) = flow {
        characterRoomDAO.getCharacterById(id).collect { character ->
            if (character != null) {
                emit(character)
            } else {
                fetchCharacterById(id).collect {
                    emit(it)
                }
            }
        }
    }

    private suspend fun fetchCharacterById(id: Int) = flow {
        val response = apiService.getCharacterById(id)

        if (response is ApiResponse.Success) {
            characterRoomDAO.insertCharacter(response.data.toCharacter())
        }

        emit(response)
    }
}
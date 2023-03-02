package ru.svoyakmartin.featureCharacter.data

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.dataSource.getEpisodesIds
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRoomDAO: CharacterRoomDAO,
    private val settings: SettingsFeatureApi,
    private val apiService: CharactersApi,
    private val locationFeatureApi: LocationFeatureApi,
    private val episodeFeatureApi: EpisodeFeatureApi,
    private val dependenciesFeatureApi: CharacterDependenciesFeatureApi,
    statisticFeatureApi: StatisticFeatureApi
) {
    val allCharacters = characterRoomDAO.getAllCharacters()
    val charactersCount = statisticFeatureApi.getCharactersCount()
    private var charactersLastPage =
        settings.readInt(SettingsFeatureApi.CHARACTERS_LAST_PAGE_KEY, 1)

    suspend fun filteredCharacter(search: String) = flow {
        var currentCount = 0
        var allCount = 0
        var filteredCount = 0

        allCharacters
            .combine(charactersCount) { characterList, allCountOrError ->
                currentCount = characterList.size
                if (allCountOrError is Int) {
                    allCount = allCountOrError
                }

                return@combine true
            }
            .combine(characterRoomDAO.getFilteredCharacters("*$search*")) { _, filteredCharacterList ->
                filteredCount = filteredCharacterList.size

                return@combine filteredCharacterList
            }.collect{
                emit(it)

                if (filteredCount < 20 && currentCount < allCount) {
                    fetchNextCharactersPartFromWeb()
                } else {
                    emit(true)
                }
            }
    }

    suspend fun fetchNextCharactersPartFromWeb(): Any {
        val response = apiService.getCharacters(charactersLastPage)

        if (response is ApiResponse.Success) {
            val charactersList = ArrayList<Character>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, Set<Int>>()

            response.data.apply {
                results.forEach { characterDTO ->
                    val character = characterDTO.toCharacter()
                    charactersList.add(character)
                    charactersAndEpisodesIdsMap[character.id] = characterDTO.getEpisodesIds()
                }

                characterRoomDAO.insertCharacters(charactersList)
                dependenciesFeatureApi.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)

                if (info.pages > charactersLastPage) {
                    settings.saveInt(
                        SettingsFeatureApi.CHARACTERS_LAST_PAGE_KEY,
                        ++charactersLastPage
                    )
                }
            }

            return true
        } else {
            return response
        }
    }

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

    suspend fun getLocationsMapByIds(locationsIdsList: Set<Int>) = flow {
        locationFeatureApi.getLocationMapByIds(locationsIdsList).collect { emit(it) }
    }

    suspend fun getEpisodesMapByCharacterId(characterId: Int) = flow {
        dependenciesFeatureApi.getEpisodesIdsByCharacterId(characterId).collect { episodeIdsList ->
            episodeFeatureApi.getEpisodeMapByIds(episodeIdsList).collect { emit(it) }
        }
    }
}
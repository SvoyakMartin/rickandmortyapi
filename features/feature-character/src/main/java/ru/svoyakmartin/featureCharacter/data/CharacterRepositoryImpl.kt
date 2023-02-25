package ru.svoyakmartin.featureCharacter.data

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

    suspend fun fetchNextCharactersPartFromWeb() {
        val response = apiService.getCharacters(charactersLastPage)
        response.body()?.apply {
            val charactersList = ArrayList<Character>()
            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()

            results.forEach { characterDTO ->
                val character = characterDTO.toCharacter()
                charactersList.add(character)
                charactersAndEpisodesIdsMap[character.id] = characterDTO.getEpisodesIds()
            }

            dependenciesFeatureApi.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)
            characterRoomDAO.insertCharacters(charactersList)

            if (info.pages > charactersLastPage) {
                settings.saveInt(
                    SettingsFeatureApi.CHARACTERS_LAST_PAGE_KEY,
                    ++charactersLastPage
                )
            }
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
            response.data.apply {
                val character = toCharacter()
                characterRoomDAO.insertCharacter(character)

                emit(character)
            }
        }
    }

    suspend fun getLocationsMapByIds(locationsIdsList: List<Int>) = flow {
        locationFeatureApi.getLocationMapByIds(locationsIdsList).collect { emit(it) }
    }

    suspend fun getEpisodesMapByCharacterId(characterId: Int) = flow {
        dependenciesFeatureApi.getEpisodesIdsByCharacterId(characterId).collect { episodeIdsList ->
            episodeFeatureApi.getEpisodeMapByIds(episodeIdsList).collect { emit(it) }
        }
    }
}
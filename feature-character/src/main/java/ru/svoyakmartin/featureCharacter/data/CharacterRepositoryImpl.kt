package ru.svoyakmartin.featureCharacter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRoomDAO: CharacterRoomDAO,
    private val settings: SettingsFeatureApi,
    private val apiService: CharactersApi,
    private val locationFeatureApi: LocationFeatureApi
) {
    val allCharacters = characterRoomDAO.getAllCharacters()
    private var charactersLastPage =
        settings.readInt(SettingsFeatureApi.CHARACTERS_LAST_PAGE_KEY, 1)
////    private lateinit var statistic: Map<String, Int>
//
////    suspend fun getStatistic() {
////        apiService.getStatistic().body()?.let {
////            statistic = it.toMap()
////        }
////    }

    suspend fun fetchNextCharactersPartFromWeb() {
        val response = apiService.getCharacters(charactersLastPage)
        response.body()?.apply {
            val charactersList = ArrayList<Character>()

            results.forEach { characterDTO ->
                charactersList.add(characterDTO.toCharacter())
            }

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

    private suspend fun fetchCharacterById(id: Int) = flow<Character> {
        val response = apiService.getCharacterById(id)

        response.body()?.apply {
            val character = toCharacter()
            characterRoomDAO.insertCharacter(character)

            emit(character)
        }
    }

    suspend fun getLocationMapById(locationId: Int) = flow {
        locationFeatureApi.getLocationMapById(locationId).collect { emit(it) }
    }

    suspend fun fetchCharactersByIds(ids: String) {
//        val response = apiService.getCharactersById(ids)
////        response.body()?.apply {
////            val charactersList = ArrayList<Character>()
////            val charactersAndEpisodesIdsMap = mutableMapOf<Int, List<Int>>()
////
////            forEach { characterDTO ->
////                val character = characterDTO.toCharacter()
////                charactersList.add(character)
////                charactersAndEpisodesIdsMap[characterDTO.id] = characterDTO.getEpisodesIds()
////            }
////
////            roomDAO.insertCharactersAndDependencies(charactersList, charactersAndEpisodesIdsMap)
////        }
    }
}
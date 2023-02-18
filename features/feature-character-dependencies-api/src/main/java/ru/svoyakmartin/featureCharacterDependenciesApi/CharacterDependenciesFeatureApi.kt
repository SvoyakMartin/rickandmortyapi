package ru.svoyakmartin.featureCharacterDependenciesApi

import kotlinx.coroutines.flow.Flow

interface CharacterDependenciesFeatureApi {
     fun getCharactersIdsByEpisodeId(episodeId: Int): Flow<List<Int>>
     fun getCharactersIdsByLocationId(locationId: Int): Flow<List<Int>>
     fun getEpisodesIdsByCharacterId(characterId: Int): Flow<List<Int>>

    suspend fun insertLocationsAndCharacters(locationsAndCharactersIdsMap: Map<Int, List<Int>>)
    suspend fun insertEpisodesAndCharacters(episodesAndCharactersIdsMap: Map<Int, List<Int>>)
    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, List<Int>>)
}
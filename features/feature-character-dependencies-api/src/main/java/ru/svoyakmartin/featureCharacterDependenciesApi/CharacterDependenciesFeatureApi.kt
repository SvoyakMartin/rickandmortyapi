package ru.svoyakmartin.featureCharacterDependenciesApi

import kotlinx.coroutines.flow.Flow

interface CharacterDependenciesFeatureApi {
     fun getCharactersIdsByEpisodeId(episodeId: Int): Flow<Set<Int>>
     fun getCharactersIdsByLocationId(locationId: Int): Flow<Set<Int>>
     fun getEpisodesIdsByCharacterId(characterId: Int): Flow<Set<Int>>

    suspend fun insertLocationsAndCharacters(locationsAndCharactersIdsMap: Map<Int, Set<Int>>)
    suspend fun insertEpisodesAndCharacters(episodesAndCharactersIdsMap: Map<Int, Set<Int>>)
    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, Set<Int>>)
}
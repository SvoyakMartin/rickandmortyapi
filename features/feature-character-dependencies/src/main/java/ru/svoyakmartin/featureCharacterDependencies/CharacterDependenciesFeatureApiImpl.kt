package ru.svoyakmartin.featureCharacterDependencies

import ru.svoyakmartin.featureCharacterDependencies.data.ExportRepositoryImpl
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import javax.inject.Inject

class CharacterDependenciesFeatureApiImpl @Inject constructor(private val repository: ExportRepositoryImpl) :
    CharacterDependenciesFeatureApi {

    override fun getCharactersIdsByEpisodeId(episodeId: Int) =
        repository.getCharactersIdsByEpisodeId(episodeId)

    override fun getCharactersIdsByLocationId(locationId: Int) =
        repository.getCharactersIdsByLocationId(locationId)

    override fun getEpisodesIdsByCharacterId(characterId: Int) =
        repository.getEpisodesIdsByCharacterId(characterId)

    override suspend fun insertLocationsAndCharacters(locationsAndCharactersIdsMap: Map<Int, Set<Int>>) =
        repository.insertLocationsAndCharacters(locationsAndCharactersIdsMap)

    override suspend fun insertEpisodesAndCharacters(episodesAndCharactersIdsMap: Map<Int, Set<Int>>) =
        repository.insertEpisodesAndCharacters(episodesAndCharactersIdsMap)

    override suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, Set<Int>>) =
        repository.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)
}
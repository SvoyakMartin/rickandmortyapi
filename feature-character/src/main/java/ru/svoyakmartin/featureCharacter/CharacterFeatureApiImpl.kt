package ru.svoyakmartin.featureCharacter

import androidx.fragment.app.Fragment
import ru.svoyakmartin.featureCharacter.data.ExportRepositoryImpl
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterDetailsFragment
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterFeatureFlowFragment
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import javax.inject.Inject

class CharacterFeatureApiImpl @Inject constructor(private val repository: ExportRepositoryImpl) :
    CharacterFeatureApi {

    override fun getFlowFragment(): Fragment = CharacterFeatureFlowFragment()

    override fun getDetailFragment(characterId: Int): Fragment =
        CharacterDetailsFragment.newInstance(characterId)

    override fun getCharacterMapByLocationId(locationId: Int) =
        repository.getCharacterMapByLocationId(locationId)

    override fun getCharacterMapByEpisodeId(episodeId: Int) =
        repository.getCharacterMapByEpisodeId(episodeId)


    override suspend fun insertCharactersAndLocations(charactersAndLocationsIdsMap: Map<Int, List<Int>>) {
        repository.insertCharactersAndLocations(charactersAndLocationsIdsMap)
    }

    override suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, List<Int>>) {
        repository.insertCharactersAndEpisodes(charactersAndEpisodesIdsMap)
    }

}
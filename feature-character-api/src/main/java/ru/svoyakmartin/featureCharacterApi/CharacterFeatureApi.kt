package ru.svoyakmartin.featureCharacterApi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter

interface CharacterFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(characterId: Int): Fragment
    fun getCharacterMapByLocationId(locationId: Int): Flow<Map<String, Int>>
    fun getCharacterMapByEpisodeId(episodeId: Int): Flow<Map<String, Int>>
    suspend fun insertCharactersAndLocations(charactersAndLocationsIdsMap: Map<Int, List<Int>>)
    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesIdsMap: Map<Int, List<Int>>)

    companion object {
        const val BACKSTACK_KEY = "characters"
    }
}
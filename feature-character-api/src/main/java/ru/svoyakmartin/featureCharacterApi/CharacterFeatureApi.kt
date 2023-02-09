package ru.svoyakmartin.featureCharacterApi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter

interface CharacterFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(characterId: String): Fragment
    fun getCharactersByIds(ids: List<Int>): Flow<List<Any>>
}
package ru.svoyakmartin.featureCharacterApi

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface CharacterFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(characterId: Int): Fragment
    fun getCharacterMapByIds(characterIdsList: Set<Int>): Flow<Any>
}
package ru.svoyakmartin.featureCharacterApi

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCore.domain.model.EntityMap

interface CharacterFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(characterId: Int): Fragment
    fun getCharacterMapByIds(characterIdsList: List<Int>): Flow<List<EntityMap>>

    companion object {
        const val BACKSTACK_KEY = "characters"
    }
}
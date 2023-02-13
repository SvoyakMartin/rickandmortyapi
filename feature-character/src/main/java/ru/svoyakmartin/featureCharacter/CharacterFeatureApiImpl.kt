package ru.svoyakmartin.featureCharacter

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCharacter.data.ExportRepositoryImpl
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterDetailsFragment
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterFeatureFlowFragment
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import javax.inject.Inject

class CharacterFeatureApiImpl @Inject constructor(private val repository: ExportRepositoryImpl) :
    CharacterFeatureApi {

    override fun getFlowFragment(): Fragment = CharacterFeatureFlowFragment()

    override fun getDetailFragment(characterId: Int): Fragment =
        CharacterDetailsFragment.newInstance(characterId)

    override fun getCharacterMapByIds(characterIdsList: List<Int>): Flow<List<EntityMap>> =
        repository.getCharacterMapByIds(characterIdsList)
}
package ru.svoyakmartin.featureCharacter

import ru.svoyakmartin.featureCharacter.data.ExportRepositoryImpl
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterDetailsFragment
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterListFragment
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import javax.inject.Inject

class CharacterFeatureApiImpl @Inject constructor(private val repository: ExportRepositoryImpl) :
    CharacterFeatureApi {

    override fun getFlowFragment() = CharacterListFragment()

    override fun getDetailFragment(characterId: Int) =
        CharacterDetailsFragment.newInstance(characterId)

    override fun getCharacterMapByIds(characterIdsList: Set<Int>) =
        repository.getCharacterMapByIds(characterIdsList)
}
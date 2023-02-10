package ru.svoyakmartin.featureCharacter

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterDetailsFragment
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterFeatureFlowFragment
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import javax.inject.Inject

class CharacterFeatureApiImpl @Inject constructor() : CharacterFeatureApi {

    override fun getFlowFragment(): Fragment = CharacterFeatureFlowFragment()

    override fun getDetailFragment(characterId: Int): Fragment =
        CharacterDetailsFragment.newInstance(characterId)
}
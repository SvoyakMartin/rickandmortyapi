package ru.svoyakmartin.featureCharacter.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureCharacter.CharacterFeatureApiImpl
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi

@Module
interface CharacterFeatureApiModule {
    @Binds
    fun bindCharacterFeatureApi(api: CharacterFeatureApiImpl): CharacterFeatureApi
}
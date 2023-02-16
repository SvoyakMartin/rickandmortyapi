package ru.svoyakmartin.featureCharacterDependencies.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureCharacterDependencies.CharacterDependenciesFeatureApiImpl
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi

@Module
interface CharacterDependenciesFeatureApiModule {

    @Binds
    fun bindCharacterDependenciesFeatureApi(api: CharacterDependenciesFeatureApiImpl): CharacterDependenciesFeatureApi
}
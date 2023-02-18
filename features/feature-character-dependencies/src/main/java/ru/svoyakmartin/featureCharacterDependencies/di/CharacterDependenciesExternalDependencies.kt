package ru.svoyakmartin.featureCharacterDependencies.di

import android.content.Context
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi

interface CharacterDependenciesExternalDependencies : FeatureExternalDependencies {
    val context: Context
    val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
}
package ru.svoyakmartin.featureLocation.di

import android.content.Context
import retrofit2.Retrofit
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

interface LocationExternalDependencies : FeatureExternalDependencies {
    val retrofit: Retrofit
    val context: Context
    val settingsFeatureApi: SettingsFeatureApi
    val characterFeatureApi: CharacterFeatureApi
    val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
}
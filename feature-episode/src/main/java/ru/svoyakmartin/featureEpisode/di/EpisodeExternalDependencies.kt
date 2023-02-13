package ru.svoyakmartin.featureEpisode.di

import android.content.Context
import retrofit2.Retrofit
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

interface EpisodeExternalDependencies : FeatureExternalDependencies {
    val retrofit: Retrofit
    val context: Context
    val settingsFeatureApi: SettingsFeatureApi
    val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
    val characterFeatureApi: CharacterFeatureApi
}
package ru.svoyakmartin.featureCharacter.di

import android.content.Context
import retrofit2.Retrofit
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter
import ru.svoyakmartin.featureCharacterDependenciesApi.CharacterDependenciesFeatureApi
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

interface CharacterExternalDependencies : FeatureExternalDependencies {
    val retrofit: Retrofit
    val context: Context
    val globalRouter: GlobalRouter
    val settingsFeatureApi: SettingsFeatureApi
    val locationFeatureApi: LocationFeatureApi
    val episodeFeatureApi: EpisodeFeatureApi
    val characterDependenciesFeatureApi: CharacterDependenciesFeatureApi
}
package ru.svoyakmartin.featureHomeScreen.di

import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

interface HomeScreenExternalDependencies: FeatureExternalDependencies {
    val characterFeatureApi: CharacterFeatureApi
    val locationFeatureApi: LocationFeatureApi
    val episodeFeatureApi: EpisodeFeatureApi
    val settingsFeatureApi: SettingsFeatureApi
    val homeScreenNavSource: HomeScreenNavSource
}
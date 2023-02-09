package ru.svoyakmartin.featureHomeScreen.di

import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

interface HomeScreenExternalDependencies: FeatureExternalDependencies {
    val characterFeatureApi: CharacterFeatureApi
    val settingsFeatureApi: SettingsFeatureApi
    val globalRouter: GlobalRouter
}
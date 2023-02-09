package ru.svoyakmartin.featureCharacter.di

import android.content.Context
import retrofit2.Retrofit
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

interface CharacterExternalDependencies : FeatureExternalDependencies {
    val retrofit: Retrofit
    val context: Context
    val globalRouter: GlobalRouter
    val settingsFeatureApi: SettingsFeatureApi
}
package ru.svoyakmartin.featureSettings.di

import android.content.Context
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter

interface SettingsExternalDependencies: FeatureExternalDependencies {
    val context: Context
    val globalRouter: GlobalRouter
}
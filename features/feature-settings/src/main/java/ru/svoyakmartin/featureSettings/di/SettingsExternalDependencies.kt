package ru.svoyakmartin.featureSettings.di

import android.content.Context
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies

interface SettingsExternalDependencies: FeatureExternalDependencies {
    val context: Context
}
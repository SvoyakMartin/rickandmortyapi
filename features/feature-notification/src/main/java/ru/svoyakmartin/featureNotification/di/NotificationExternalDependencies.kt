package ru.svoyakmartin.featureNotification.di

import android.content.Context
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi

interface NotificationExternalDependencies : FeatureExternalDependencies {
    val context: Context
    val characterFeatureApi: CharacterFeatureApi
}
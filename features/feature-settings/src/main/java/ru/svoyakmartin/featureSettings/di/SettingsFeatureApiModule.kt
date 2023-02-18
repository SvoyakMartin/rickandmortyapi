package ru.svoyakmartin.featureSettings.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureSettings.SettingsFeatureApiImpl
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi

@Module(includes = [SettingsModule::class])
interface SettingsFeatureApiModule {
    @Binds
    fun bindSettingsFeatureApi(api: SettingsFeatureApiImpl): SettingsFeatureApi
}
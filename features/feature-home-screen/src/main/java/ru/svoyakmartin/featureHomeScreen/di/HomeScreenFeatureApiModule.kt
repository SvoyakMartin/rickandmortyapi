package ru.svoyakmartin.featureHomeScreen.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureHomeScreen.HomeScreenFeatureApiImpl
import ru.svoyakmartin.featureHomeScreenApi.HomeScreenFeatureApi

@Module
interface HomeScreenFeatureApiModule {
    @Binds
    fun bindHomeScreenFeatureApi(api: HomeScreenFeatureApiImpl): HomeScreenFeatureApi
}
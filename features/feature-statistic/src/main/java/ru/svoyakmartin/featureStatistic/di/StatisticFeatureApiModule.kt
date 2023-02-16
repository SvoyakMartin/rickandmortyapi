package ru.svoyakmartin.featureStatistic.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureStatistic.StatisticFeatureApiImpl
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi

@Module
interface StatisticFeatureApiModule {

    @Binds
    fun bindEpisodeFeatureApi(api: StatisticFeatureApiImpl): StatisticFeatureApi
}
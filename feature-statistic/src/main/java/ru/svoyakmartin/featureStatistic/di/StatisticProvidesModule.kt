package ru.svoyakmartin.featureStatistic.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.svoyakmartin.featureStatistic.data.dataSource.StatisticApi


@Module
class StatisticProvidesModule {
    @Provides
    fun provideLocationApi(retrofit: Retrofit): StatisticApi = retrofit.create()
}

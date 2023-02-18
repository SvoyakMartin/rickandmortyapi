package ru.svoyakmartin.featureEpisode.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureEpisode.EpisodeFeatureApiImpl
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi

@Module
interface EpisodeFeatureApiModule {

    @Binds
    @Suppress("UNUSED")
    fun bindEpisodeFeatureApi(api: EpisodeFeatureApiImpl): EpisodeFeatureApi
}
package ru.svoyakmartin.featureStatisticApi

import kotlinx.coroutines.flow.Flow

interface StatisticFeatureApi {
    fun getCharactersCount(): Flow<Any>
    fun getLocationsCount(): Flow<Any>
    fun getEpisodesCount(): Flow<Any>
}
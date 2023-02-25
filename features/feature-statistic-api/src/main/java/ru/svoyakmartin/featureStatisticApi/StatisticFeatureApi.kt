package ru.svoyakmartin.featureStatisticApi

import kotlinx.coroutines.flow.Flow

interface StatisticFeatureApi {
    fun getCharactersCount(): Flow<Int>
    fun getLocationsCount(): Flow<Int>
    fun getEpisodesCount(): Flow<Int>
}
package ru.svoyakmartin.featureStatistic

import ru.svoyakmartin.featureStatistic.data.StatisticRepositoryImpl
import ru.svoyakmartin.featureStatisticApi.StatisticFeatureApi
import javax.inject.Inject

class StatisticFeatureApiImpl @Inject constructor(private val repository: StatisticRepositoryImpl) :
    StatisticFeatureApi {
    override fun getCharactersCount() = repository.getCharactersCount()
    override fun getLocationsCount() = repository.getLocationsCount()
    override fun getEpisodesCount() = repository.getEpisodesCount()
}
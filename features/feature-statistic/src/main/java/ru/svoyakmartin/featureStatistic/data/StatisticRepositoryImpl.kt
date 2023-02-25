package ru.svoyakmartin.featureStatistic.data

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureStatistic.CHARACTERS_FIELD
import ru.svoyakmartin.featureStatistic.EPISODES_FIELD
import ru.svoyakmartin.featureStatistic.LOCATIONS_FIELD
import ru.svoyakmartin.featureStatistic.data.dataSource.StatisticApi
import ru.svoyakmartin.featureStatistic.data.dataSource.toMap
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val apiService: StatisticApi
) {
    private lateinit var statistic: Map<String, Int>

    private suspend fun loadStatistic() {
        apiService.getStatistic().body()?.let {
            statistic = it.toMap()
        }
    }

    fun getCharactersCount() = flow {
        loadStatistic()
        emit(statistic[CHARACTERS_FIELD] ?: 0)
    }

    fun getLocationsCount() = flow {
        loadStatistic()
        emit(statistic[LOCATIONS_FIELD] ?: 0)
    }

    fun getEpisodesCount() = flow {
        loadStatistic()
        emit(statistic[EPISODES_FIELD] ?: 0)
    }
}
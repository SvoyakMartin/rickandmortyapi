package ru.svoyakmartin.featureStatistic.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureStatistic.CHARACTERS_FIELD
import ru.svoyakmartin.featureStatistic.EPISODES_FIELD
import ru.svoyakmartin.featureStatistic.LOCATIONS_FIELD
import ru.svoyakmartin.featureStatistic.data.dataSource.StatisticApi
import ru.svoyakmartin.featureStatistic.data.dataSource.toMap
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val apiService: StatisticApi
) {
    private val statistic = flow {
        when (val response = apiService.getStatistic()) {
            is ApiResponse.Success -> {
                emit(response.data.toMap())
            }
            else -> emit(response)
        }
    }.flowOn(Dispatchers.IO)

    private fun getValueOrError(field: String) = flow {
        statistic.collect { response ->
            when (response) {
                is Map<*, *> -> {
                    emit(response[field] ?: 0)
                }
                else -> {
                    emit(response)
                }
            }
        }
    }

    fun getCharactersCount() = flow {
        getValueOrError(CHARACTERS_FIELD).collect { emit(it) }
    }

    fun getLocationsCount() = flow {
        getValueOrError(LOCATIONS_FIELD).collect { emit(it) }
    }

    fun getEpisodesCount() = flow {
        getValueOrError(EPISODES_FIELD).collect { emit(it) }
    }
}
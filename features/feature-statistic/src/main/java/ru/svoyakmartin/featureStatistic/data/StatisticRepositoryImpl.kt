package ru.svoyakmartin.featureStatistic.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureStatistic.data.dataSource.StatisticApi
import ru.svoyakmartin.featureStatistic.data.dataSource.toMap
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

@AppScope
class StatisticRepositoryImpl @Inject constructor(
    private val apiService: StatisticApi
) {
    private lateinit var statistic: Map<String, Int>

    init {
        CoroutineScope(EmptyCoroutineContext).launch{
            loadStatistic()
        }
    }

    private suspend fun loadStatistic() {
        apiService.getStatistic().body()?.let {
            statistic = it.toMap()
        }
    }
}
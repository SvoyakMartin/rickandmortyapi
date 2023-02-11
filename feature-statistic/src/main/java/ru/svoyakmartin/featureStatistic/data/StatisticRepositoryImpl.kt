package ru.svoyakmartin.featureStatistic.data

import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureStatistic.data.dataSource.StatisticApi
import javax.inject.Inject

@AppScope
class StatisticRepositoryImpl @Inject constructor(
    private val apiService: StatisticApi
) {
//    private lateinit var statistic: Map<String, Int>
//
//    suspend fun getStatistic() {
//        apiService.getStatistic().body()?.let {
//            statistic = it.toMap()
//        }
//    }
}
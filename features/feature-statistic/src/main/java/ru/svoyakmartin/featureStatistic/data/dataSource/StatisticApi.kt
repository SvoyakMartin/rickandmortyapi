package ru.svoyakmartin.featureStatistic.data.dataSource

import retrofit2.http.GET
import retrofit2.http.Url
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureStatistic.data.model.Root

interface StatisticApi {
    @GET
    suspend fun getStatistic(@Url url: String = "https://rickandmortyapi.com/page-data/sq/d/1506520932.json"): ApiResponse<Root>
}
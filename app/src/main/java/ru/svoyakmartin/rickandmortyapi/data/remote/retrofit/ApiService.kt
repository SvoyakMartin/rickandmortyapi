package ru.svoyakmartin.rickandmortyapi.data.remote.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.svoyakmartin.rickandmortyapi.data.remote.models.*

interface ApiService {
    @GET
    suspend fun getStatistic(@Url url: String = "https://rickandmortyapi.com/page-data/sq/d/1506520932.json"): Response<Root>
}
package ru.svoyakmartin.featureEpisode.data.dataSource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO
import ru.svoyakmartin.featureEpisode.data.model.EpisodesDTO

interface EpisodesApi {
    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): Response<EpisodesDTO>

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Response<EpisodeDTO>

    @GET("episode/[{id}]")
    suspend fun getEpisodesByIds(@Path("id") ids: String): Response<List<EpisodeDTO>>
}
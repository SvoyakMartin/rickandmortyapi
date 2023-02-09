package ru.svoyakmartin.rickandmortyapi.data.remote.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.svoyakmartin.rickandmortyapi.data.remote.models.*

interface ApiService {
    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): Response<LocationsDTO>

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: String): Response<LocationDTO>

    @GET("location/[{id}]")
    suspend fun getLocationsById(@Path("id") id: String): Response<List<LocationDTO>>

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): Response<EpisodesDTO>

    @GET("episode/[{id}]")
    suspend fun getEpisodesByIds(@Path("id") id: String): Response<List<EpisodeDTO>>

    @GET
    suspend fun getStatistic(@Url url: String = "https://rickandmortyapi.com/page-data/sq/d/1506520932.json"): Response<Root>
}
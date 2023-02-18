package ru.svoyakmartin.featureLocation.data.dataSource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.svoyakmartin.featureLocation.data.model.LocationDTO
import ru.svoyakmartin.featureLocation.data.model.LocationsDTO

interface LocationsApi {
    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): Response<LocationsDTO>

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): Response<LocationDTO>

    @GET("location/[{id}]")
    suspend fun getLocationsByIds(@Path("id") ids: String): Response<List<LocationDTO>>
}
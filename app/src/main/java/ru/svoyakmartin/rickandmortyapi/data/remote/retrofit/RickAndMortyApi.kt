package ru.svoyakmartin.rickandmortyapi.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://rickandmortyapi.com/api/"
private val contentType = "application/json".toMediaType()

private val client = OkHttpClient()
    .newBuilder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
    .build()

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory(contentType))
    .client(client)
    .build()

object RickAndMortyApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
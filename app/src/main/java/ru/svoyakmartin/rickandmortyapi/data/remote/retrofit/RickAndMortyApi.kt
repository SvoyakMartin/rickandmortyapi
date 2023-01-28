package ru.svoyakmartin.rickandmortyapi.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.svoyakmartin.rickandmortyapi.BuildConfig

const val BASE_URL = "https://rickandmortyapi.com/api/"

fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BASIC
                else
                    HttpLoggingInterceptor.Level.NONE
            )
        )
        .build()
}

fun getJson(): Json {
    return Json { ignoreUnknownKeys = true }
}

@OptIn(ExperimentalSerializationApi::class)
fun getRetrofit (json: Json): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        // TODO: add call
        .client(getOkHttpClient())
        .build()
}

class RickAndMortyApi(retrofit: Retrofit) {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
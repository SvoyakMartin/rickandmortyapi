package ru.svoyakmartin.rickandmortyapi.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.svoyakmartin.rickandmortyapi.BuildConfig
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.ApiService
import ru.svoyakmartin.rickandmortyapi.di.annotations.AppScope

@Module
class NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }

    @AppScope
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @AppScope
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        jsonConverterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverterFactory)
            // TODO: add call
            .client(okHttpClient)
            .build()
    }

    @AppScope
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @AppScope
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BASIC
            else
                HttpLoggingInterceptor.Level.NONE
        )
    }

    @AppScope
    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun provideJsonConverterFactory(): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    fun provideBaseUrl() = "https://rickandmortyapi.com/api/"
}
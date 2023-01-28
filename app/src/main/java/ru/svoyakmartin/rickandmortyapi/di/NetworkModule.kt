package ru.svoyakmartin.rickandmortyapi.di

import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.ApiService
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.RickAndMortyApi
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.getJson
import ru.svoyakmartin.rickandmortyapi.data.remote.retrofit.getRetrofit

@Module
class NetworkModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return RickAndMortyApi(retrofit).retrofitService
    }

    @Provides
    fun provideRetrofit(json: Json): Retrofit {
        return getRetrofit(json)
    }

    @Provides
    fun provideJson(): Json {
        return getJson()
    }
}
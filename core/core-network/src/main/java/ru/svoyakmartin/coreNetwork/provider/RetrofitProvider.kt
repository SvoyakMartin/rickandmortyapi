package ru.svoyakmartin.coreNetwork.provider

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponseCallAdapterFactory

object RetrofitProvider {
    fun get(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(ApiResponseCallAdapterFactory())
        .client(okHttpClient)
        .build()
}
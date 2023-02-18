package ru.svoyakmartin.coreNetwork.provider

import okhttp3.Interceptor
import okhttp3.OkHttpClient

object OkHttpClientProvider {
    fun get(interceptors: Set<@JvmSuppressWildcards Interceptor>) : OkHttpClient{
        val builder = OkHttpClient.Builder()
        interceptors.forEach(builder::addInterceptor)

        return builder.build()
    }
}
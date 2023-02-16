package ru.svoyakmartin.coreNetwork.provider

import okhttp3.logging.HttpLoggingInterceptor
import ru.svoyakmartin.coreNetwork.BuildConfig

object HttpLoggingInterceptorProvider {
    fun get(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BASIC
            else
                HttpLoggingInterceptor.Level.NONE
        )
}
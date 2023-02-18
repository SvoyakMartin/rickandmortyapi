package ru.svoyakmartin.coreNetwork.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.svoyakmartin.coreNetwork.provider.HttpLoggingInterceptorProvider
import ru.svoyakmartin.coreNetwork.provider.JsonConverterFactoryProvider
import ru.svoyakmartin.coreNetwork.provider.OkHttpClientProvider
import ru.svoyakmartin.coreNetwork.provider.RetrofitProvider

@Module
object NetworkModule {
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        jsonConverterFactory: Converter.Factory
    ): Retrofit = RetrofitProvider.get(baseUrl, okHttpClient, jsonConverterFactory)

    @Provides
    fun provideOkHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
        OkHttpClientProvider.get(interceptors)

    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptorProvider.get()

    @Provides
    fun provideJsonConverterFactory(): Converter.Factory = JsonConverterFactoryProvider.get()

    @Provides
    fun provideBaseUrl() = "https://rickandmortyapi.com/api/"
}
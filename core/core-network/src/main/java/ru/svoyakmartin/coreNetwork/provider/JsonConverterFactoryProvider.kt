package ru.svoyakmartin.coreNetwork.provider

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

object JsonConverterFactoryProvider {
    private val json = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "not_use"
    }
    private val contentType = "application/json".toMediaType()

    fun get(): Converter.Factory {
        @OptIn(ExperimentalSerializationApi::class)
        return json.asConverterFactory(contentType)
    }
}
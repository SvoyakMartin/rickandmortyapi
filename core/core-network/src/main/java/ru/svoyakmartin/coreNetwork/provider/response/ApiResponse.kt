package ru.svoyakmartin.coreNetwork.provider.response

import java.io.IOException

sealed interface ApiResponse<out T> {
    class Success<T>(val data: T) : ApiResponse<T>

    sealed interface Failure : ApiResponse<Nothing> {
        data class NetworkFailure(val error: IOException) : Failure

        data class HttpFailure(val code: Int, val message: String) : Failure

        data class ApiFailure(val error: Throwable) : Failure

        data class UnknownFailure(val error: Throwable) : Failure
    }
}
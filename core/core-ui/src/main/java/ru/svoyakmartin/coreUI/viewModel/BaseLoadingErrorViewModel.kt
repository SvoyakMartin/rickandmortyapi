package ru.svoyakmartin.coreUI.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.NetworkFailure
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.HttpFailure
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.ApiFailure
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.UnknownFailure

open class BaseLoadingErrorViewModel : ViewModel() {
    private val _error = MutableStateFlow(mapOf<String, Any>())
    val error = _error.stateFlowWithDelay()
    var isErrorShowing = false

    private fun resetError() {
        _error.value = mapOf()
        isErrorShowing = false
        setIsLoading(false)
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.stateFlowWithDelay()

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun getDataOrSetError(value: Any) = flow {
        when (value) {
            is ApiResponse.Failure -> {
                setError(value)
            }
            is ApiResponse.Success<*> -> {
                emit(value.data)
            }
            else -> {
                emit(value)
            }
        }

    }

    private fun setError(failure: ApiResponse.Failure) {
        _error.value = when (failure) {
            is NetworkFailure -> {
                mapOf(
                    "title" to "Network failure",
                    "message" to (failure.error.message ?: failure.error),
                    "listener" to { resetError() }
                )
            }
            is HttpFailure -> {
                mapOf(
                    "title" to "HTTP failure",
                    "message" to ("${failure.code}: ${failure.message}"),
                    "listener" to { resetError() }
                )
            }
            is ApiFailure -> {
                mapOf(
                    "title" to "API failure",
                    "message" to (failure.error.message ?: failure.error),
                    "listener" to { resetError() }
                )
            }
            is UnknownFailure -> {
                mapOf(
                    "title" to "Unknown failure",
                    "message" to (failure.error.message ?: failure.error),
                    "listener" to { resetError() }
                )
            }
        }
    }

    fun <T> StateFlow<T>.stateFlowWithDelay() = stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        value
    )
}
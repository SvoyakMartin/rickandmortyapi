package ru.svoyakmartin.coreNetwork.provider.response

import android.util.Log
import kotlinx.serialization.SerializationException
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.NetworkFailure
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.ApiFailure
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse.Failure.UnknownFailure


class ApiResponseCall(private val call: Call<ApiResponse<*>>) : Call<ApiResponse<*>> by call {
    override fun enqueue(callback: Callback<ApiResponse<*>>) =
        call.enqueue(object : Callback<ApiResponse<*>> {

            override fun onResponse(
                call: Call<ApiResponse<*>>,
                response: Response<ApiResponse<*>>
            ) {
                val requestUrl = (call.request() as Request).url
                val requestCode = response.code()

                if (response.isSuccessful) {
                    Log.i("", "Success $requestUrl")

                    callback.onResponse(
                        call,
                        Response.success(ApiResponse.Success(checkNotNull(response.body())))
                    )
                } else {
                    Log.w("", "HTTP failure: $requestCode | $requestUrl")

                    callback.onResponse(
                        call,
                        Response.success(
                            ApiResponse.Failure.HttpFailure(
                                code = requestCode,
                                message = response.message()
                            )
                        )
                    )
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<*>>, error: Throwable
            ) {
                val requestUrl = (call.request() as Request).url

                when (error) {
                    is IOException -> {
                        Log.w("", "Network failure: $requestUrl \n\n $error")

                        callback.onResponse(call, Response.success(NetworkFailure(error)))
                    }

                    is SerializationException -> {
                        Log.e("", "Api failure: $requestUrl \n\n $error")

                        callback.onResponse(call, Response.success(ApiFailure(error)))
                    }

                    else -> {
                        Log.e("", "Unknown failure: $requestUrl \n\n $error")

                        callback.onResponse(call, Response.success(UnknownFailure(error)))
                    }
                }
            }
        })

}
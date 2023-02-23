package ru.svoyakmartin.coreNetwork.provider.response

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null

        check(returnType is ParameterizedType) { "Not parametrized Call return type" }

        val innerType = getParameterUpperBound(0, returnType)

        if (getRawType(returnType) != ApiResponse::class.java) return null

        check(innerType is ParameterizedType) { "Not parametrized ApiResponse return type" }
        val responseType = getParameterUpperBound(0, innerType)

        return ApiResponseCallAdapter(responseType)
    }
}

package ru.svoyakmartin.rickandmortyapi.presentation.util

import android.content.Context
import android.os.Build
import android.os.Bundle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.di.AppComponent
import java.io.Serializable

@Suppress("UNUSED")
val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

fun <T> Flow<T>.stateInStarted5000(
    scope: CoroutineScope,
    initialValue: T
): StateFlow<T> {
    return stateIn(scope, SharingStarted.WhileSubscribed(5000), initialValue)
}

fun <T : Serializable?> Bundle.serializable(key: String, clazz: Class<T>): T {
    @Suppress("UNCHECKED_CAST")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        getSerializable(key, clazz)!!
    else
        @Suppress("DEPRECATION")
        (getSerializable(key) as T)
}

package ru.svoyakmartin.rickandmortyapi.presentation.util

import android.content.Context
import android.os.Build
import android.os.Bundle
import ru.svoyakmartin.rickandmortyapi.App
import ru.svoyakmartin.rickandmortyapi.di.AppComponent
import java.io.Serializable


fun <T : Serializable?> Bundle.serializable(key: String, clazz: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        getSerializable(key, clazz)!!
    else
        @Suppress("DEPRECATION")
        (getSerializable(key) as T)
}

val Context.appComponent: AppComponent
    get() = when (this){
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
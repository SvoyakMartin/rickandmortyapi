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

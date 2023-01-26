package ru.svoyakmartin.rickandmortyapi.presentation.util

import android.os.Build
import android.os.Bundle
import java.io.Serializable


fun <T : Serializable?> Bundle.serializable(key: String, clazz: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        getSerializable(key, clazz)!!
    else
        @Suppress("DEPRECATION")
        (getSerializable(key) as T)
}

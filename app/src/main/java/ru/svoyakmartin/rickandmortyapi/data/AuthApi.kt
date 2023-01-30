package ru.svoyakmartin.rickandmortyapi.data

import ru.svoyakmartin.rickandmortyapi.data.repository.TempRepository
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.LoginFragment.Companion.CHARACTER_KEY

object AuthApi {
    fun auth(login: String, password: String): Map<String, Any> {
        return if (login == "admin" && password == "admin") {
            mapOf(CHARACTER_KEY to TempRepository().getNewItem(0))
        } else {
            mapOf("error" to "Неверный логин или пароль!")
        }
    }
}
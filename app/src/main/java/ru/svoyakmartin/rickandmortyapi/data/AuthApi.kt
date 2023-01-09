package ru.svoyakmartin.rickandmortyapi.data

import io.reactivex.Maybe
import ru.svoyakmartin.rickandmortyapi.models.Character

object AuthApi {

    fun auth(login: String, password: String): Maybe<Character?> {
        return if (login == "admin" && password == "admin") {
            Maybe.just(getNewItem(0))

        } else {
            Maybe.empty()
        }
    }
}
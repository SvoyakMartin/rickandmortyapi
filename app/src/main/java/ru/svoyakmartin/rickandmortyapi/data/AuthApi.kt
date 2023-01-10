package ru.svoyakmartin.rickandmortyapi.data

import io.reactivex.Single
import ru.svoyakmartin.rickandmortyapi.models.Character

object AuthApi {

    fun auth(login: String, password: String): Single<Character?> {
        return if (login == "admin" && password == "admin") {
            Single.just(getNewItem(0))

        } else {
            Single.error(Throwable("Неверный логин или пароль!"))
        }
    }
}
package ru.svoyakmartin.rickandmortyapi.screens.main.login

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import ru.svoyakmartin.rickandmortyapi.data.AuthApi
import ru.svoyakmartin.rickandmortyapi.models.Character

class LoginViewModel : ViewModel() {
    private val repository = AuthApi

    fun login(login: String, password: String): Single<Character?> {
        return repository.auth(login, password)
    }


}
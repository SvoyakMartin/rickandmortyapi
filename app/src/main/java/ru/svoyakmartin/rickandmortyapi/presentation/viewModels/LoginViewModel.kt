package ru.svoyakmartin.rickandmortyapi.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.svoyakmartin.rickandmortyapi.data.AuthApi
import ru.svoyakmartin.rickandmortyapi.domain.models.Character
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.LoginFragment.Companion.CHARACTER_KEY

class LoginViewModel : ViewModel() {
    private val repository = AuthApi
    private val _character = MutableLiveData<Character?>()
    val character: LiveData<Character?>
        get() = _character
    private val _error = MutableLiveData<String>().apply{
        value = ""
    }
    val error: LiveData<String>
        get() = _error

    fun login(login: String, password: String): Job = viewModelScope.launch {
        val request = repository.auth(login, password)

        if (request.containsKey("error")) {
            _error.value = request["error"].toString()
        }

        if (request.containsKey(CHARACTER_KEY)) {
            _character.value = request[CHARACTER_KEY] as Character?
        }
    }

    fun clear(){
        _error.value = ""
        _character.value = null
    }


}
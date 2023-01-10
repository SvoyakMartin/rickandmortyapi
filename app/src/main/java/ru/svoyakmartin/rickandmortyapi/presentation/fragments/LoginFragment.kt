package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentLoginBinding
import ru.svoyakmartin.rickandmortyapi.domain.models.Character
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.LoginViewModel
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    /** Создать фрагмент для авторизации состоящий из двух полей ввода loginEditText и passwordEditText, а также кнопки loginButton.
     * Кнопка должна быть неактивна, пока пользователь не введет логин и пароль.
     * При нажатии на кнопку должен происходить запрос на авторизацию. (замокать ответ)
     * Если авторизация прошла успешно, то должен открыться фрагмент с информацией о пользователе.
     * Если авторизация не прошла, то должен показаться сообщение об ошибке.
     * Все запросы должны быть выполнены с помощью RxJava.*/
    companion object {
        const val CHARACTER_KEY = "character"
        private const val DEBOUNCE_TIMEOUT = 1000L
    }

    private val disposables = CompositeDisposable()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            val loginObservable = loginEditText.textChanges()
            disposables.add(
                loginObservable.debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onNext = {
                            loginLayout.error = null
                        }
                    )
            )

            val passwordObservable = passwordEditText.textChanges()
            disposables.add(
                passwordObservable.debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onNext = {
                            passwordLayout.error = null
                        }
                    )
            )

            disposables.add(
                Observable.combineLatest(
                    loginObservable,
                    passwordObservable
                ) { login, password -> login.trim().isNotEmpty() && password.trim().isNotEmpty() }
                    .subscribe {
                        loginButton.isEnabled = it
                    }
            )

            loginButton.apply {
                isEnabled = false
                setOnClickListener {
                    it.isEnabled = false
                    authFromApi()
                }
            }
        }
    }

    private fun authFromApi() {
        with(binding) {
            disposables.add(
                viewModel.login(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        /* onSuccess() */ {
                            goToInfoFragment(it)
                        },
                        /* onError() */ {
                            onLoginError(it.message.toString())
                        }
                    )
            )
        }
    }

    private fun onLoginError(message: String) {
        with(binding) {
            loginLayout.error = message
            passwordLayout.error = message
        }
    }

    private fun goToInfoFragment(character: Character? = null) {
        val infoFragment = InfoFragment().apply {
            arguments = bundleOf(CHARACTER_KEY to character)
        }

        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("UserStack")
            .replace(R.id.fragmentContainerView, infoFragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}
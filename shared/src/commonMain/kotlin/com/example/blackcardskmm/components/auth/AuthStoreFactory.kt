package com.example.blackcardskmm.components.auth

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.data.models.requests.LoginRequestDto
import com.example.blackcardskmm.domain.repository.AuthRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<AuthRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): AuthStore =
        object : AuthStore, Store<AuthStore.Intent, AuthStore.State, Nothing> by storeFactory.create(
            name = "AuthStore",
            initialState = AuthStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class EmailChanged(val email: String) : Msg()
        data class PasswordChanged(val password: String) : Msg()
        data class PasswordVisibilityChanged(val passwordVisibility: Boolean) : Msg()
        object SignInProcessing : Msg()
        object SignInSuccessful : Msg()
        data class SignInFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<AuthStore.Intent, Unit, AuthStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> AuthStore.State) {
            val state = getState()
            if (state.isValid()) {
                signIn(getState().email, getState().password)
            }
        }

        override fun executeIntent(intent: AuthStore.Intent, getState: () -> AuthStore.State): Unit =
            when (intent) {
                is AuthStore.Intent.EmailChanged -> dispatch(Msg.EmailChanged(intent.value))
                is AuthStore.Intent.PasswordChanged -> dispatch(Msg.PasswordChanged(intent.value))
                is AuthStore.Intent.PasswordVisibilityChanged -> dispatch(Msg.PasswordVisibilityChanged(intent.value))
                is AuthStore.Intent.SignInConfirmed -> signIn(getState().email, getState().password)
            }

        private fun signIn(email: String, password: String) {
            scope.launch {
                repository.login(LoginRequestDto(email, password))
                    .onStart { dispatch(Msg.SignInProcessing) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.SignInSuccessful)
                            is Result.Error -> dispatch(Msg.SignInFailed(error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<AuthStore.State, Msg> {
        override fun AuthStore.State.reduce(msg: Msg): AuthStore.State =
            when (msg) {
                is Msg.SignInProcessing -> copy(isLoading = true)
                is Msg.SignInSuccessful -> copy(isLoading = false, isSignedIn = true)
                is Msg.SignInFailed -> copy(isLoading = false, error = msg.error)
                is Msg.EmailChanged -> copy(email = msg.email)
                is Msg.PasswordChanged -> copy(password = msg.password)
                is Msg.PasswordVisibilityChanged -> copy(passwordVisibility = msg.passwordVisibility)
            }
    }
}
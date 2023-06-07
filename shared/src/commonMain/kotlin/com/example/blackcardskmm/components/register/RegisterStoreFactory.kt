package com.example.blackcardskmm.components.register

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.data.models.requests.RegisterRequestDto
import com.example.blackcardskmm.domain.repository.AuthRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RegisterStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<AuthRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): RegisterStore =
        object : RegisterStore, Store<RegisterStore.Intent, RegisterStore.State, Nothing> by storeFactory.create(
            name = "RegisterStore",
            initialState = RegisterStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class UsernameChanged(val username: String) : Msg()
        data class EmailChanged(val email: String) : Msg()
        data class PasswordChanged(val password: String) : Msg()
        data class RepeatedPasswordChanged(val password: String) : Msg()
        data class PasswordVisibilityChanged(val passwordVisibility: Boolean) : Msg()
        object RegistrationProcessing : Msg()
        object RegistrationSuccessful : Msg()
        data class RegistrationFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<RegisterStore.Intent, Unit, RegisterStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> RegisterStore.State) {
            register(getState().username, getState().email, getState().password)
        }

        override fun executeIntent(intent: RegisterStore.Intent, getState: () -> RegisterStore.State): Unit =
            when (intent) {
                is RegisterStore.Intent.UsernameChanged -> dispatch(Msg.UsernameChanged(intent.value))
                is RegisterStore.Intent.EmailChanged -> dispatch(Msg.EmailChanged(intent.value))
                is RegisterStore.Intent.PasswordChanged -> dispatch(Msg.PasswordChanged(intent.value))
                is RegisterStore.Intent.RepeatedPasswordChanged -> dispatch(Msg.RepeatedPasswordChanged(intent.value))
                is RegisterStore.Intent.PasswordVisibilityChanged -> dispatch(Msg.PasswordVisibilityChanged(intent.value))
                is RegisterStore.Intent.RegistrationConfirmed -> register(getState().username, getState().email, getState().password)
            }

        private fun register(username: String, email: String, password: String) {
            scope.launch {
                repository.register(RegisterRequestDto(username, email, password))
                    .onStart { dispatch(Msg.RegistrationProcessing) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.RegistrationSuccessful)
                            is Result.Error -> dispatch(Msg.RegistrationFailed(error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<RegisterStore.State, Msg> {
        override fun RegisterStore.State.reduce(msg: Msg): RegisterStore.State =
            when (msg) {
                is Msg.RegistrationProcessing -> copy(isLoading = true)
                is Msg.RegistrationSuccessful -> copy(isLoading = false, isRegistered = true)
                is Msg.RegistrationFailed -> copy(isLoading = false, error = msg.error)
                is Msg.UsernameChanged -> copy(email = msg.username)
                is Msg.EmailChanged -> copy(email = msg.email)
                is Msg.PasswordChanged -> copy(password = msg.password)
                is Msg.RepeatedPasswordChanged -> copy(password = msg.password)
                is Msg.PasswordVisibilityChanged -> copy(passwordVisibility = msg.passwordVisibility)
            }
    }
}
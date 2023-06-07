package com.example.blackcardskmm.components.startup

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.domain.repository.AuthRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class StartupStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<AuthRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): StartupStore =
        object : StartupStore, Store<StartupStore.Intent, StartupStore.State, Nothing> by storeFactory.create(
            name = "StartupStore",
            initialState = StartupStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        object AuthenticationProcessing : Msg()
        object AuthenticationConfirmed : Msg()
        data class AuthenticationFailed(val error: String) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<StartupStore.Intent, Unit, StartupStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> StartupStore.State) {
            checkAuthentication()
        }

        override fun executeIntent(intent: StartupStore.Intent, getState: () -> StartupStore.State) {
            checkAuthentication()
        }

        private fun checkAuthentication() {
            scope.launch {
                repository.isAuthenticated()
                    .onStart { dispatch(Msg.AuthenticationProcessing) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.AuthenticationConfirmed)
                            is Result.Error -> dispatch(Msg.AuthenticationFailed(error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<StartupStore.State, Msg> {
        override fun StartupStore.State.reduce(msg: Msg): StartupStore.State =
            when (msg) {
                is Msg.AuthenticationProcessing -> copy(isLoading = true)
                is Msg.AuthenticationConfirmed -> copy(isLoading = false, isAuthenticated = true)
                is Msg.AuthenticationFailed -> copy(isLoading = false, error = msg.error)
            }
    }
}
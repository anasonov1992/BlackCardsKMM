package com.example.blackcardskmm.components.main

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.components.main.MainComponent.NavItem
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<FractionsRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): MainStore =
        object : MainStore, Store<MainStore.Intent, MainStore.State, Nothing> by storeFactory.create(
            name = "MainStore",
            initialState = MainStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        object FractionsLoading : Msg()
        data class FractionsLoadedSuccessful(val fractions: ImmutableList<Fraction>) : Msg()
        data class FractionsLoadedFailed(val error: String?) : Msg()
        data class NavListUpdated(val navItems: List<NavItem>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<MainStore.Intent, Unit, MainStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> MainStore.State) {
            loadContent()
        }

        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) =
            when (intent) {
                is MainStore.Intent.SelectNavItem -> selectNavItem(intent.type, getState())
            }

        private fun loadContent() {
            scope.launch {
                repository.getFractions(fetchFromRemote = true)
                    .onStart { dispatch(Msg.FractionsLoading) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.FractionsLoadedSuccessful(fractions = result.data.toImmutableList()))
                            is Result.Error -> dispatch(Msg.FractionsLoadedFailed(error = result.message))
                        }
                }
            }
        }

        private fun selectNavItem(type: NavItem.Type, state: MainStore.State) {
            val newList = state.navTabItems.map { it.copy(selected = type == it.type) }
            dispatch(Msg.NavListUpdated(newList))
        }
    }

    private object ReducerImpl : Reducer<MainStore.State, Msg> {
        override fun MainStore.State.reduce(msg: Msg): MainStore.State =
            when (msg) {
                is Msg.FractionsLoading -> copy(isLoading = true)
                is Msg.FractionsLoadedSuccessful -> copy(isLoading = false, fractions = msg.fractions)
                is Msg.FractionsLoadedFailed -> copy(isLoading = false, error = msg.error)
                is Msg.NavListUpdated -> copy(navTabItems = msg.navItems)
            }
    }
}
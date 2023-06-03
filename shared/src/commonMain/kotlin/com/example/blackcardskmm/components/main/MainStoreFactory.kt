package com.example.blackcardskmm.components.main

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.components.main.MainComponent.NavItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
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
        data class NavListUpdated(val navItems: List<NavItem>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<MainStore.Intent, Nothing, MainStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) =
            when (intent) {
                is MainStore.Intent.SelectNavItem -> selectNavItem(intent.type, getState())
            }

        private fun selectNavItem(type: NavItem.Type, state: MainStore.State) {
            val newList = state.navItems.map { it.copy(selected = type == it.type) }
            dispatch(Msg.NavListUpdated(newList))
        }
    }

    private object ReducerImpl : Reducer<MainStore.State, Msg> {
        override fun MainStore.State.reduce(msg: Msg): MainStore.State =
            when (msg) {
                is Msg.NavListUpdated -> copy(navItems = msg.navItems)
            }
    }
}
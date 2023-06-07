package com.example.blackcardskmm.components.startup

import com.arkivanov.mvikotlin.core.store.Store

interface StartupStore: Store<StartupStore.Intent, StartupStore.State, Nothing> {
    sealed class Intent {
        object NavigateToStart: Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isAuthenticated: Boolean = false,
        val error: String? = null
    )
}
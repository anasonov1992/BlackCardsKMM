package com.example.blackcardskmm.components.startup

import com.arkivanov.mvikotlin.core.store.Store

interface StartupStore: Store<Nothing, StartupStore.State, Nothing> {
    data class State(
        val isLoading: Boolean = true,
        val isAuthenticated: Boolean = false,
        val error: String? = null
    )
}
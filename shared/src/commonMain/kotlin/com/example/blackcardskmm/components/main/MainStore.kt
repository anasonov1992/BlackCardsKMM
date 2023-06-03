package com.example.blackcardskmm.components.main

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.components.main.MainComponent.NavItem
import com.example.blackcardskmm.components.main.MainComponent.NavItem.Type.*

interface MainStore: Store<MainStore.Intent, MainStore.State, Nothing> {
    sealed class Intent {
        data class SelectNavItem(val type: NavItem.Type) : Intent()
    }

    data class State(
        val navItems: List<NavItem> = listOf(
            NavItem(true, FRACTIONS),
            NavItem(false, CARDARTS)
        )
    )
}
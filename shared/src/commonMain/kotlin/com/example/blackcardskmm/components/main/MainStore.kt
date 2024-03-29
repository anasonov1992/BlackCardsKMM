package com.example.blackcardskmm.components.main

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.components.main.MainComponent.NavItem
import com.example.blackcardskmm.components.main.MainComponent.NavItem.Type.*
import com.example.blackcardskmm.domain.models.Fraction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

interface MainStore: Store<MainStore.Intent, MainStore.State, Nothing> {
    sealed class Intent {
        data class SelectNavItem(val type: NavItem.Type) : Intent()
    }

    data class State(
        val navTabItems: List<NavItem> = listOf(
            NavItem(false, MENU),
            NavItem(true, FRACTIONS),
            NavItem(false, CARDARTS)
        ),
        val navMenuItems: List<NavItem> = listOf(NavItem(false, LORE), NavItem(false, DECKS)),
        val fractions: ImmutableList<Fraction> = emptyList<Fraction>().toImmutableList(),
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val error: String? = null
    )
}
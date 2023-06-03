package com.example.blackcardskmm.android.ui.navigation.models

import com.example.blackcardskmm.android.R

sealed class TabNavigationItem(
    val screen: Screen,
    val iconResId: Int,
    val selectedIconResId: Int? = null
) {
    object Menu : TabNavigationItem(
        screen = Screen.Menu,
        iconResId = R.drawable.ic_menu
    )

    object CardArts : TabNavigationItem(
        screen = Screen.CardArts,
        iconResId = R.drawable.ic_profile //FIXME
    )

    object Fractions : TabNavigationItem(
        screen = Screen.Fractions,
        iconResId = R.drawable.ic_messenger //FIXME
    )

    object Lore : TabNavigationItem(
        screen = Screen.Lore,
        iconResId = 0 //FIXME
    )

    object Decks : TabNavigationItem(
        screen = Screen.Decks,
        iconResId = 0 //FIXME
    )

    object CardDeck : TabNavigationItem(
        screen = Screen.CardDeck,
        iconResId = R.drawable.ic_calendar //FIXME
    )

    object CreateCardDeck : TabNavigationItem(
        screen = Screen.CreateCardDeck,
        iconResId = R.drawable.ic_calendar //FIXME
    )

    object CreateCardDeckSummary : TabNavigationItem(
        screen = Screen.CreateCardDeckSummary,
        iconResId = R.drawable.ic_calendar //FIXME
    )

    companion object {
        val createCardDeckNavigationItems = listOf(CreateCardDeck, CreateCardDeckSummary)
        val drawerNavigationItems = listOf(Lore, Decks)
    }
}

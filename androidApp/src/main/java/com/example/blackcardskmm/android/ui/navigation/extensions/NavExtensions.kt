package com.example.blackcardskmm.android.ui.navigation.extensions

import com.example.blackcardskmm.android.ui.navigation.models.TabNavigationItem
import com.example.blackcardskmm.android.ui.views.destinations.*
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

fun TabNavigationItem.toDirectionDestination(): DirectionDestinationSpec {
    return when (this) {
        TabNavigationItem.CardArts -> CardArtsListDestination
        TabNavigationItem.CardDeck -> CardDeckDestination
        TabNavigationItem.Fractions -> FractionsDestination
        TabNavigationItem.Lore -> LoreDestination
        TabNavigationItem.Decks -> DecksDestination
        else -> FractionsDestination
    }
}
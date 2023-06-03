package com.example.blackcardskmm.android.ui.navigation.models

import com.example.blackcardskmm.data.primitives.FractionType

sealed class NavigationEvent {
    object SignIn : NavigationEvent()
    object Register : NavigationEvent()
    object CardArts : NavigationEvent()
    object Fractions : NavigationEvent()
    object Lore : NavigationEvent()
    object Decks : NavigationEvent()

    data class CardArtDetail(val id: Int) : NavigationEvent()
    data class CardImageDetail(val imageUrl: String) : NavigationEvent()
    data class CardsLibrary(val fractionId: Int, val fractionType: FractionType) : NavigationEvent()
    data class CardDeck(val fractionId: Int) : NavigationEvent()
    data class CreateCardDeck(val fractionId: Int, val deckName: String, val deckId: Int?) : NavigationEvent()

    object NavigateUp : NavigationEvent()
}

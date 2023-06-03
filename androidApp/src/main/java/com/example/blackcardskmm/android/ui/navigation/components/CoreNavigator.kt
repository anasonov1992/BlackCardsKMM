package com.example.blackcardskmm.android.ui.navigation.components

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.ui.views.destinations.*
import com.ramcosta.composedestinations.navigation.navigate

class CoreNavigator(private val navController: NavController) : CommonNavigator {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun navigateEvent(event: NavigationEvent) {
        when (event) {
            NavigationEvent.SignIn -> {
                navController.navigate(SignInDestination)
            }
            NavigationEvent.Register -> {
                navController.navigate(RegisterDestination)
            }
            NavigationEvent.CardArts -> {
                navController.navigate(CardArtsListDestination)
            }
            NavigationEvent.Lore -> {
                navController.navigate(LoreDestination)
            }
            NavigationEvent.Decks -> {
                navController.navigate(DecksDestination)
            }
            NavigationEvent.Fractions -> {
                navController.navigate(FractionsDestination)
            }
            is NavigationEvent.CardArtDetail -> {
                navController.navigate(CardArtDetailDestination(event.id))
            }
            is NavigationEvent.CardImageDetail -> {
                navController.navigate(CardImageDetailDestination(event.imageUrl))
            }
            is NavigationEvent.CardsLibrary -> {
                navController.navigate(CardsLibraryDestination(event.fractionId, event.fractionType))
            }
            is NavigationEvent.CardDeck -> {
                navController.navigate(CardDeckDestination())
            }
            is NavigationEvent.CreateCardDeck -> {
                navController.navigate(CreateCardDeckDestination(event.fractionId, event.deckName, event.deckId))
            }
            NavigationEvent.NavigateUp -> {
                navController.popBackStack()
            }
        }
    }
}
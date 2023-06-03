package com.example.blackcardskmm.android.ui.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import com.example.blackcardskmm.android.ui.views.destinations.*

object NavGraphs {
    @OptIn(ExperimentalComposeUiApi::class)
    val root = object : NavGraphSpec {
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            StartCoverDestination,
            SignInDestination,
            RegisterDestination,
            CardArtsListDestination,
            CardArtDetailDestination,
            CardImageDetailDestination,
            FractionsDestination,
            LoreDestination,
            DecksDestination,
            CardsLibraryDestination,
            CardDeckDestination,
            CreateCardDeckDestination
        ).associateBy { it.route }

        override val route: String = "root"
        override val startRoute: Route = StartCoverDestination
    }
}
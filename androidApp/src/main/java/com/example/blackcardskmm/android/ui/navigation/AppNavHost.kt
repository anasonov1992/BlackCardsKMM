package com.example.blackcardskmm.android.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.example.blackcardskmm.android.ui.navigation.components.BackHandlerImpl
import com.example.blackcardskmm.android.ui.navigation.components.CoreNavigator
import com.example.blackcardskmm.android.ui.views.destinations.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun AppNavHost(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    drawerGesturesEnabledState: MutableState<Boolean>
) {
    DestinationsNavHost(
        navController = navController,
        navGraph = NavGraphs.root,
        dependenciesContainerBuilder = {
            dependency(CoreNavigator(navController))
            dependency(scaffoldState)
            dependency(drawerGesturesEnabledState)
            dependency(BackHandlerImpl())

            dependency(StartCoverDestination)
            dependency(SignInDestination)
            dependency(RegisterDestination)
            dependency(CardArtsListDestination)
            dependency(CardArtDetailDestination)
            dependency(CardImageDetailDestination)
            dependency(FractionsDestination)
            dependency(CardsLibraryDestination)
            dependency(CardDeckDestination)
            dependency(CreateCardDeckDestination)

            // Drawer Menu items
            dependency(LoreDestination)
            dependency(DecksDestination)
        }
    )
}
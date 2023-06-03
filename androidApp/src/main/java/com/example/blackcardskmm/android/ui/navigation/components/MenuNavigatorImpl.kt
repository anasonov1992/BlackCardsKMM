package com.example.blackcardskmm.android.ui.navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.blackcardskmm.android.ui.navigation.extensions.toDirectionDestination
import com.example.blackcardskmm.android.ui.navigation.interfaces.MenuNavigator
import com.example.blackcardskmm.android.ui.navigation.models.TabNavigationItem
import com.example.blackcardskmm.android.ui.views.destinations.*
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationFlow
import com.ramcosta.composedestinations.utils.destination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuNavigatorImpl(private val navController: NavController) : MenuNavigator {
    override fun navigate(item: TabNavigationItem) {
        if (navController.currentDestination?.route !== item.toDirectionDestination().route) {
            navController.navigate(item.toDirectionDestination()) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    override fun isNavItemSelectedAsFlow(item: TabNavigationItem): Flow<Boolean> {
        return navController.currentDestinationFlow.map { it === item.toDirectionDestination() }
    }

    @Composable
    override fun isMainRoute(): Boolean {
        return when (navController.currentBackStackEntryAsState().value?.destination()) {
            CardArtsListDestination -> true
            CardDeckDestination -> true
            FractionsDestination -> true
            else -> false
        }
    }
}
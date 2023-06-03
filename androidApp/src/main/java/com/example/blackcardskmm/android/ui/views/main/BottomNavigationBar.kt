package com.example.blackcardskmm.android.ui.views.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.blackcardskmm.android.ui.navigation.components.TabsBottomNavigation
import com.example.blackcardskmm.components.main.MainComponent.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationBar(
    navItems: List<NavItem>,
    onNavItemSelected: (NavItem) -> Unit,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    BottomAppBar {
        TabsBottomNavigation(
            items = navItems,
            onNavItemClick = { navItem ->
                if (navItem.type != NavItem.Type.MENU) {
                    onNavItemSelected(navItem)
                }
                else {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
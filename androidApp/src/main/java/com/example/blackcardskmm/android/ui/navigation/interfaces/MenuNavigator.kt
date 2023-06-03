package com.example.blackcardskmm.android.ui.navigation.interfaces

import androidx.compose.runtime.Composable
import com.example.blackcardskmm.android.ui.navigation.models.TabNavigationItem
import kotlinx.coroutines.flow.Flow

interface MenuNavigator {
    fun navigate(item: TabNavigationItem)
    fun isNavItemSelectedAsFlow(item: TabNavigationItem): Flow<Boolean>

    @Composable
    fun isMainRoute(): Boolean
}
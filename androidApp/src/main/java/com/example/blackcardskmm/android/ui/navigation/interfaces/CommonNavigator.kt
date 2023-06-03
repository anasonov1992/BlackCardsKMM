package com.example.blackcardskmm.android.ui.navigation.interfaces

import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent

interface CommonNavigator {
    fun navigateEvent(event: NavigationEvent)
}
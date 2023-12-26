package com.example.blackcardskmm.android.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.blackcardskmm.android.ui.views.cards.CardsLibrary
import com.example.blackcardskmm.android.ui.views.cards.CreateCardDeck
import com.example.blackcardskmm.android.ui.views.decks.Decks
import com.example.blackcardskmm.android.ui.views.lore.Lore
import com.example.blackcardskmm.android.ui.views.main.Main
import com.example.blackcardskmm.components.RootComponent

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
)
@Composable
internal fun Root(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade()),
    ) {
        when(val child = it.instance) {
            is RootComponent.Child.Startup -> Startup(child.component)
            is RootComponent.Child.Auth -> SignIn(child.component)
            is RootComponent.Child.Register -> Register(child.component)
            is RootComponent.Child.Main -> Main(child.component)
            is RootComponent.Child.Lore -> Lore(child.component)
            is RootComponent.Child.Decks -> Decks(child.component)
            is RootComponent.Child.CreateCardDeck -> CreateCardDeck(child.component)
            is RootComponent.Child.CardsLibrary -> CardsLibrary(child.component)
        }
    }
}
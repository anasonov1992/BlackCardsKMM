package com.example.blackcardskmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.blackcardskmm.android.ui.theme.BlackCardsTheme
import com.example.blackcardskmm.components.RootComponent
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.blackcardskmm.android.ui.views.Root

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent =
            RootComponent(
                componentContext = defaultComponentContext(),
                storeFactory = DefaultStoreFactory(),
            )

        setContent {
            BlackCardsTheme {
                Root(
                    component = rootComponent
                )
            }
        }
    }
}


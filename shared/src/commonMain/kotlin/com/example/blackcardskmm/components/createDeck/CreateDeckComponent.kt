package com.example.blackcardskmm.components.createDeck

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.blackcardskmm.util.CustomDispatchers

class CreateCardDeckComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
    ) : ComponentContext by componentContext {

    sealed class Output {
    }
}
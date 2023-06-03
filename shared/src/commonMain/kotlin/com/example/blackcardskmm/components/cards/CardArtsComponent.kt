package com.example.blackcardskmm.components.cards

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.blackcardskmm.components.fractions.FractionsComponent
import com.example.blackcardskmm.components.fractions.FractionsStore
import com.example.blackcardskmm.components.fractions.FractionsStoreFactory
import com.example.blackcardskmm.util.CustomDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class CardArtsComponent (
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
    ) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        CardArtsStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CardArtsStore.State> = store.stateFlow

    fun onEvent(event: CardArtsStore.Intent) {
        store.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateToCardArtDetail(val artId: Int): Output()
    }
}
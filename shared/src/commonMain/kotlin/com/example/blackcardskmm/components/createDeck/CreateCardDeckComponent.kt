package com.example.blackcardskmm.components.createDeck

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class CreateCardDeckComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    fractionId: Int,
    deckId: Int?,
    deckName: String? = null,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        CreateCardDeckStoreFactory(
            storeFactory = storeFactory,
            fractionId = fractionId,
            deckId = deckId,
            deckName = deckName
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CreateCardDeckStore.State> = store.stateFlow

    fun onEvent(event: CreateCardDeckStore.Intent) {
        store.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object NavigateBack: Output()
    }
}
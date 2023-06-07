package com.example.blackcardskmm.components.cards

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class CardArtDetailComponent (
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    artId: Int,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        CardArtDetailStoreFactory(
            storeFactory = storeFactory,
            artId = artId
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CardArtDetailStore.State> = store.stateFlow

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object NavigateBack : Output()
    }
}
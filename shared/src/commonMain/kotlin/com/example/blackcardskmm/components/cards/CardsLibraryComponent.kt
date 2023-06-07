package com.example.blackcardskmm.components.cards

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.blackcardskmm.data.primitives.FractionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class CardsLibraryComponent (
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    fractionId: Int,
    fractionType: FractionType,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        CardsLibraryStoreFactory(
            storeFactory = storeFactory,
            fractionId = fractionId,
            fractionType = fractionType
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CardsLibraryStore.State> = store.stateFlow

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateToCardDetail(val cardId: Int): Output() //FIXME
        object NavigateBack : Output()
    }
}
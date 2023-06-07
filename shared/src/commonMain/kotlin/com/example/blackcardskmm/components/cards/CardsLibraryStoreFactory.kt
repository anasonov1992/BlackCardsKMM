package com.example.blackcardskmm.components.cards

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.CardSpell
import com.example.blackcardskmm.domain.models.CardUnit
import com.example.blackcardskmm.domain.repository.CardsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CardsLibraryStoreFactory(
    private val storeFactory: StoreFactory,
    private val fractionId: Int,
    private val fractionType: FractionType
): KoinComponent {
    private val repository by inject<CardsRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): CardsLibraryStore =
        object : CardsLibraryStore, Store<Nothing, CardsLibraryStore.State, Nothing> by storeFactory.create(
            name = "CardsLibraryStore",
            initialState = CardsLibraryStore.State(fractionType = fractionType),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class CardsLibraryLoading(val cardUnitsFlow: Flow<PagingData<CardUnit>>, val cardSpellsFlow: Flow<PagingData<CardSpell>>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Unit, CardsLibraryStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CardsLibraryStore.State) {
            loadContentAsFlow(fractionId)
        }

        private fun loadContentAsFlow(fractionId: Int) {
            dispatch(Msg.CardsLibraryLoading(
                cardUnitsFlow = repository.getCardUnits(fractionId),
                cardSpellsFlow = repository.getCardSpells(fractionId))
            )
        }
    }

    private object ReducerImpl: Reducer<CardsLibraryStore.State, Msg> {
        override fun CardsLibraryStore.State.reduce(msg: Msg): CardsLibraryStore.State =
            when (msg) {
                is Msg.CardsLibraryLoading -> copy(
                    cardUnitsFlow = msg.cardUnitsFlow,
                    cardSpellsFlow = msg.cardSpellsFlow
                )
            }
    }
}
package com.example.blackcardskmm.components.cards

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.domain.models.CardArt
import com.example.blackcardskmm.domain.repository.CardsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CardArtsStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<CardsRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): CardArtsStore =
        object : CardArtsStore, Store<CardArtsStore.Intent, CardArtsStore.State, Nothing> by storeFactory.create(
            name = "CardArtsStore",
            initialState = CardArtsStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class CardArtsLoading(val cardArtsFlow: Flow<PagingData<CardArt>>) : Msg()
        data class SearchActivated(val isActive: Boolean): Msg()
        data class SearchProcessing(val filter: String?, val cardArtsFlow: Flow<PagingData<CardArt>>): Msg()
        data class SearchCleared(val cardArtsFlow: Flow<PagingData<CardArt>>): Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<CardArtsStore.Intent, Unit, CardArtsStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CardArtsStore.State) {
            setupTextSearch(getState().textSearch)
            loadCardArtsAsFlow()
        }

        override fun executeIntent(intent: CardArtsStore.Intent, getState: () -> CardArtsStore.State) =
            when (intent) {
                is CardArtsStore.Intent.SearchActivated -> dispatch(Msg.SearchActivated(isActive = intent.isActive))
                is CardArtsStore.Intent.SearchProcessing -> getState().setSearchText(filter = intent.filter)
                is CardArtsStore.Intent.SearchCleared -> getState().clearSearchText()
            }

        private fun setupTextSearch(textSearch: StateFlow<String>) {
            scope.launch {
                textSearch.debounce(500).collect { query ->
                    if (query.isNullOrEmpty()) {
                        dispatch(Msg.SearchCleared(cardArtsFlow = repository.getCardArtsAsFlow()))
                    }
                    else {
                        dispatch(Msg.SearchProcessing(filter = query, cardArtsFlow = repository.getCardArtsAsFlow(query)))
                    }
                }
            }
        }

        private fun loadCardArtsAsFlow(filter: String? = null) {
            dispatch(Msg.CardArtsLoading(cardArtsFlow = repository.getCardArtsAsFlow(filter)))
        }
    }

    private object ReducerImpl: Reducer<CardArtsStore.State, Msg> {
        override fun CardArtsStore.State.reduce(msg: Msg): CardArtsStore.State =
            when (msg) {
                is Msg.CardArtsLoading -> copy(cardArts = msg.cardArtsFlow)
                is Msg.SearchActivated -> copy(isSearchActive = msg.isActive)
                is Msg.SearchProcessing -> copy(cardArts = msg.cardArtsFlow)
                is Msg.SearchCleared -> copy(cardArts = msg.cardArtsFlow)
            }
    }
}
package com.example.blackcardskmm.components.decks

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.domain.models.DeckGroup
import com.example.blackcardskmm.domain.repository.DecksRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class DecksStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<DecksRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): DecksStore =
        object : DecksStore,
            Store<Nothing, DecksStore.State, Nothing> by storeFactory.create(
                name = "DecksStore",
                initialState = DecksStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        object DecksLoading : Msg()
        data class DecksLoaded(val decks: List<DeckGroup>) : Msg()
        data class DecksFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Unit, DecksStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> DecksStore.State) {
            loadDecks()
        }

        private fun loadDecks() {
            scope.launch {
                repository.getDecks()
                    .onStart { dispatch(Msg.DecksLoading) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.DecksLoaded(decks = result.data))
                            is Result.Error -> dispatch(Msg.DecksFailed(error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<DecksStore.State, Msg> {
        override fun DecksStore.State.reduce(msg: Msg): DecksStore.State =
            when (msg) {
                is Msg.DecksLoading -> copy(isLoading = true)
                is Msg.DecksLoaded -> copy(isLoading = false, myDecks = msg.decks.toImmutableList()) //TODO: add shared decks
                is Msg.DecksFailed -> copy(isLoading = false, error = msg.error)
            }
    }
}
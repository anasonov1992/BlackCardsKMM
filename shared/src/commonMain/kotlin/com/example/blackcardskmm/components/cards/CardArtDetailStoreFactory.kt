package com.example.blackcardskmm.components.cards

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.domain.models.CardArtDetail
import com.example.blackcardskmm.domain.repository.CardsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CardArtDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val artId: Int
): KoinComponent {
    private val repository by inject<CardsRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create():  CardArtDetailStore =
        object :  CardArtDetailStore, Store<Nothing,  CardArtDetailStore.State, Nothing> by storeFactory.create(
            name = " CardArtDetailStore",
            initialState =  CardArtDetailStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        object DetailLoading : Msg()
        data class DetailLoadingSuccessful(val detail: CardArtDetail) : Msg()
        data class DetailLoadingFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Unit, CardArtDetailStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CardArtDetailStore.State) {
            loadContent()
        }

        private fun loadContent() {
            scope.launch {
                repository.getCardArtDetail(artId)
                    .onStart { dispatch(Msg.DetailLoading) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.DetailLoadingSuccessful(detail = result.data))
                            is Result.Error -> dispatch(Msg.DetailLoadingFailed(error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<CardArtDetailStore.State, Msg> {
        override fun CardArtDetailStore.State.reduce(msg: Msg): CardArtDetailStore.State =
            when (msg) {
                is Msg.DetailLoading -> copy(isLoading = true)
                is Msg.DetailLoadingSuccessful -> copy(detail = msg.detail)
                is Msg.DetailLoadingFailed -> copy(error = msg.error)
            }
    }
}
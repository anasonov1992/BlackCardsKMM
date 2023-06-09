package com.example.blackcardskmm.components.fractions

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class FractionsStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {
    private val repository by inject<FractionsRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): FractionsStore =
        object : FractionsStore, Store<Nothing, FractionsStore.State, Nothing> by storeFactory.create(
            name = "FractionsStore",
            initialState = FractionsStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        object FractionsLoading : Msg()
        data class FractionsLoaded(val fractions: List<Fraction>) : Msg()
        data class FractionsFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Unit, FractionsStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> FractionsStore.State) {
            loadFractions()
        }

        private fun loadFractions() {
            scope.launch {
                repository.getFractions(fetchFromRemote = false)
                    .onStart { dispatch(Msg.FractionsLoading) }
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.FractionsLoaded(fractions = result.data))
                            is Result.Error -> dispatch(Msg.FractionsFailed(error = result.message))
                        }
                    }
                }
            }
    }

    private object ReducerImpl: Reducer<FractionsStore.State, Msg> {
        override fun FractionsStore.State.reduce(msg: Msg): FractionsStore.State =
            when (msg) {
                is Msg.FractionsLoading -> copy(isLoading = true)
                is Msg.FractionsLoaded -> copy(isLoading = false, fractions = msg.fractions)
                is Msg.FractionsFailed -> copy(isLoading = false, error = msg.error)
            }
    }
}
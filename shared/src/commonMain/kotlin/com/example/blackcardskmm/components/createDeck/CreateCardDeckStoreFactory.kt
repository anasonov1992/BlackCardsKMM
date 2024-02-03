package com.example.blackcardskmm.components.createDeck

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.blackcardskmm.data.models.CardInDeckPairDto
import com.example.blackcardskmm.data.models.CreateDeckDto
import com.example.blackcardskmm.domain.models.CardDeckRankGroup
import com.example.blackcardskmm.domain.repository.DecksRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CreateCardDeckStoreFactory(
    private val storeFactory: StoreFactory,
    private val fractionId: Int,
    private val deckId: Int?,
    private val deckName: String?
): KoinComponent {
    private val repository by inject<DecksRepository>()
    private val dispatchers by inject<CustomDispatchers>()

    fun create(): CreateCardDeckStore =
        object : CreateCardDeckStore,
            Store<CreateCardDeckStore.Intent, CreateCardDeckStore.State, Nothing> by storeFactory.create(
                name = "CreateCardDeckStore",
                initialState = CreateCardDeckStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class CardsLoadedSuccessfully(val maxCardsCount: Int, val cardsDeck: ImmutableList<CardDeckRankGroup>) : Msg()
        data class CardsLoadingFailed(val error: String?) : Msg()
        data object CardsDeckCreatedSuccessfully : Msg()
        data class CardsDeckCreatingFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<CreateCardDeckStore.Intent, Unit, CreateCardDeckStore.State, Msg, Nothing>(dispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CreateCardDeckStore.State) {
            loadContent()
        }

        override fun executeIntent(intent: CreateCardDeckStore.Intent, getState: () -> CreateCardDeckStore.State) =
            when (intent) {
                is CreateCardDeckStore.Intent.CreateCardDeck -> createCardDeck(getState())
            }

        private fun loadContent() {
            scope.launch {
                val cardsDeckFlow: Flow<Result<List<CardDeckRankGroup>>> =
                    if (deckId == null)
                        repository.getFractionCards(fractionId = fractionId)
                    else
                        repository.getDeckCards(deckId = deckId, fractionId = fractionId)

                cardsDeckFlow.collectLatest { result ->
                    when (result) {
                        is Result.Success -> dispatch(
                            Msg.CardsLoadedSuccessfully(
                                maxCardsCount = result.data.sumOf { it.rankInfo.maxCardsOfRankCount },
                                cardsDeck = result.data.toImmutableList())
                        )
                        is Result.Error -> dispatch(Msg.CardsLoadingFailed(error = result.message))
                    }
                }
            }
        }

        fun createCardDeck(cardsDeckState: CreateCardDeckStore.State) {
            if (deckName.isNullOrEmpty()) {
                dispatch(Msg.CardsDeckCreatingFailed(error = "Укажите название колоды"))
                return
            }

            cardsDeckState.run {
                if (cardsInDeckCount < maxCardsCount) {
                    dispatch(Msg.CardsDeckCreatingFailed(error = "Соберите колоду полностью. " +
                            "Вам не хватает ${maxCardsCount - cardsInDeckCount} карт"))
                    return
                }
            }

            scope.launch {
                repository.createDeck(CreateDeckDto(
                    fractionId = fractionId,
                    deckName = deckName,
                    cardIds = cardsDeckState.cardsDeck
                        .flatMap { it.cards }
                        .filter { it.amountInDeck.value > 0 }
                        .map { CardInDeckPairDto(it.id, it.amountInDeck.value) }
                    ))
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> dispatch(Msg.CardsDeckCreatedSuccessfully)
                            is Result.Error -> dispatch(Msg.CardsDeckCreatingFailed(error = result.message))
                        }
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<CreateCardDeckStore.State, Msg> {
        override fun CreateCardDeckStore.State.reduce(msg: Msg): CreateCardDeckStore.State =
            when (msg) {
                is Msg.CardsLoadedSuccessfully -> copy(
                    maxCardsCount = msg.maxCardsCount,
                    cardsDeck = msg.cardsDeck
                )
                is Msg.CardsDeckCreatedSuccessfully -> this //FIXME
                is Msg.CardsLoadingFailed -> copy(error = msg.error)
                is Msg.CardsDeckCreatingFailed -> copy(error = msg.error)
            }
    }
}
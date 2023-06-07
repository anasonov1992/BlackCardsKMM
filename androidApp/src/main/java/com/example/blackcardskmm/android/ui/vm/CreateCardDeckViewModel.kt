package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcardskmm.android.ui.states.CardDeckState
import com.example.blackcardskmm.data.models.CardInDeckPairDto
import com.example.blackcardskmm.data.models.CreateDeckDto
import com.example.blackcardskmm.domain.repository.DecksRepository
import com.example.blackcardskmm.util.Result
import com.example.blackcardskmm.util.ObservableLoader
import com.example.blackcardskmm.domain.models.CardDeckRankGroup
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateCardDeckViewModel constructor(
    private val repository: DecksRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel() {
//    private val navArgs = savedStateHandle.navArgs<CreateCardDeckArgs>()
//    private val fractionId = navArgs.fractionId
//    private val deckId = navArgs.deckId
//    private val deckName = navArgs.deckName
//
//    private val _uiEvent = MutableSharedFlow<UiEvent>()
//    val uiEvent = _uiEvent.asSharedFlow()
//
//    private val _loadingStatus = ObservableLoader()
//    val loadingStatus = _loadingStatus.flow
//
//    var state by mutableStateOf(CardDeckState.EMPTY)
//
//    init {
//        loadContent()
//    }
//
//    private fun loadContent() {
//        viewModelScope.launch {
//            val cardsDeckFlow: Flow<Result<List<CardDeckRankGroup>>> =
//                if (deckId == null)
//                    repository.getFractionCards(fractionId = fractionId)
//                else
//                    repository.getDeckCards(deckId = deckId)
//
//            cardsDeckFlow.collectLatest { result ->
//                when (result) {
//                    is Result.Success -> {
//                        state = state.copy(
//                            maxCardsCount = result.data.sumOf { it.rankInfo.maxCardsOfRankCount },
//                            cardsDeck = result.data.toImmutableList()
//                        )
//                    }
//                    is Result.Error -> {
//                        state = CardDeckState.EMPTY
//                    }
//                }
//            }
//        }
//    }
//
//    fun createDeck() {
//        viewModelScope.launch {
//            repository.createDeck(CreateDeckDto(
//                fractionId = fractionId,
//                deckName = deckName,
//                cardIds = state.cardsDeck
//                    .flatMap { it.cards }
//                    .filter { it.amountInDeck.value > 0 }
//                    .map { CardInDeckPairDto(it.id, it.amountInDeck.value) }
//            ))
//                .onStart { _loadingStatus.start() }
//                .onCompletion { _loadingStatus.stop() }
//                .collectLatest { result ->
//                    _uiEvent.emit(
//                        when (result) {
//                            is Result.Success -> success()
//                            is Result.Error -> showMessage(result.message)
//                        }
//                    )
//            }
//        }
//    }
}
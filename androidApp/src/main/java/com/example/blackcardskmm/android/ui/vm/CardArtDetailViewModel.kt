package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcardskmm.android.ui.events.CardArtDetailEvent
import com.example.blackcardskmm.android.ui.states.CardArtDetailState
import com.example.blackcardskmm.android.ui.views.cards.CardArtDetailArgs
import com.example.blackcardskmm.android.ui.views.navArgs
import com.example.blackcardskmm.domain.repository.CardsRepository
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.launch

class CardArtDetailViewModel constructor(
    private val repository: CardsRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val artId = savedStateHandle.navArgs<CardArtDetailArgs>().id

    var state by mutableStateOf(CardArtDetailState.EMPTY)

    init {
        loadContent()
    }

    fun onEvent(event: CardArtDetailEvent) {
        when(event) {
            is CardArtDetailEvent.Refresh -> {
//                loadContent(isRefreshing = true)
            }
        }
    }

    private fun loadContent(isRefreshing: Boolean = false) {
        //TODO refactor
        if (isRefreshing) {
            state = state.copy(isRefreshing = true)
        }

        viewModelScope.launch {
            repository.getCardArtDetail(artId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            cardArt = result.data,
                            //FIXME handle error another way
//                            error = null
                        )
                    }
                    is Result.Error -> {
                        state = CardArtDetailState.EMPTY
                        //FIXME handle error another way
//                      error = result.message
                    }
                }
            }
        }
    }
}
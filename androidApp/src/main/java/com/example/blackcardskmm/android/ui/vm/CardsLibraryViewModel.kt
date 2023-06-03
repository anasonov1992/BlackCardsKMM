package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.blackcardskmm.domain.repository.CardsRepository
import com.example.blackcardskmm.android.ui.states.CardsLibraryState
import com.example.blackcardskmm.android.ui.views.cards.CardLibraryArgs
import com.example.blackcardskmm.android.ui.views.navArgs

class CardsLibraryViewModel constructor(
    private val repository: CardsRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val fractionId = savedStateHandle.navArgs<CardLibraryArgs>().fractionId

    var state by mutableStateOf(CardsLibraryState.EMPTY)

    init {
        loadContent()
    }

    private fun loadContent() {
        state = state.copy(
            cardUnits = repository.getCardUnits(fractionId),
            cardSpells = repository.getCardSpells(fractionId)
        )
    }
}
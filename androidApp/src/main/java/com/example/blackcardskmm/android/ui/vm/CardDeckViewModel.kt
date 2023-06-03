package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.blackcardskmm.android.ui.states.CardDeckState
import com.example.blackcardskmm.domain.repository.DecksRepository

class CardDeckViewModel constructor(
    private val repository: DecksRepository
): ViewModel() {

    var state by mutableStateOf(CardDeckState.EMPTY)

    init {
        loadContent()
    }

    private fun loadContent() {

    }
}
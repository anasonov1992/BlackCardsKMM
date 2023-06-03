package com.example.blackcardskmm.android.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackcardskmm.android.ui.states.DecksState
import com.example.blackcardskmm.domain.repository.DecksRepository
import com.example.blackcardskmm.util.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DecksViewModel constructor(
    private val decksRepo: DecksRepository
): ViewModel() {
    var state by mutableStateOf(DecksState.EMPTY)

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            decksRepo.getDecks().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            myDecks = result.data.toImmutableList()
                            //TODO: add shared decks
                        )
                    }
                    is Result.Error -> {
                        //TODO
                    }
                }
            }
        }
    }
}
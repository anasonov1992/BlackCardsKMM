package com.example.blackcardskmm.android.ui.states

import androidx.paging.PagingData
import com.example.blackcardskmm.domain.models.CardSpell
import com.example.blackcardskmm.domain.models.CardUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CardsLibraryState(
    val cardUnits: Flow<PagingData<CardUnit>> = emptyFlow(),
    val cardSpells: Flow<PagingData<CardSpell>> = emptyFlow()
) {
    companion object {
        val EMPTY = CardsLibraryState()
    }
}

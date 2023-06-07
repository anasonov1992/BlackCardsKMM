package com.example.blackcardskmm.components.cards

import com.arkivanov.mvikotlin.core.store.Store
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.CardSpell
import com.example.blackcardskmm.domain.models.CardUnit
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface CardsLibraryStore : Store<Nothing, CardsLibraryStore.State, Nothing> {
    data class State(
        val fractionType: FractionType = FractionType.Unknown,
        val cardUnitsFlow: Flow<PagingData<CardUnit>> = emptyFlow(),
        val cardSpellsFlow: Flow<PagingData<CardSpell>> = emptyFlow()
    ) {
        companion object {
            val EMPTY = State()
        }
    }
}
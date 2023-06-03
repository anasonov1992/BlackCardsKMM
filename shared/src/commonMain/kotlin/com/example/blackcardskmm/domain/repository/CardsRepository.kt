package com.example.blackcardskmm.domain.repository

import com.example.blackcardskmm.domain.models.CardUnit
import com.example.blackcardskmm.domain.models.CardArt
import com.example.blackcardskmm.domain.models.CardArtDetail
import com.example.blackcardskmm.domain.models.CardSpell
import com.example.blackcardskmm.util.Result
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun getCardArtsAsFlow(filter: String? = null) : Flow<PagingData<CardArt>>

    fun getCardUnits(fractionId: Int) : Flow<PagingData<CardUnit>>

    suspend fun getCardArtDetail(id: Int) : Flow<Result<CardArtDetail>>

    fun getCardSpells(fractionId: Int) : Flow<PagingData<CardSpell>>
}
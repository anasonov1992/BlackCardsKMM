package com.example.blackcardskmm.domain.repository

import com.example.blackcardskmm.data.models.CreateDeckDto
import com.example.blackcardskmm.domain.models.CardDeckRankGroup
import com.example.blackcardskmm.domain.models.Deck
import com.example.blackcardskmm.domain.models.DeckGroup
import com.example.blackcardskmm.util.Result
import kotlinx.coroutines.flow.Flow

interface DecksRepository {
    suspend fun getFractionCards(fractionId: Int): Flow<Result<List<CardDeckRankGroup>>>

    suspend fun getDeckCards(deckId: Int): Flow<Result<List<CardDeckRankGroup>>>

    suspend fun createDeck(request: CreateDeckDto): Flow<Result<Deck>>

    suspend fun getDecks(): Flow<Result<List<DeckGroup>>>
}
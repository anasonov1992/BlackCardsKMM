package com.example.blackcardskmm.data.repository

import com.example.blackcardskmm.data.api.DecksApi
import com.example.blackcardskmm.data.models.CreateDeckDto
import com.example.blackcardskmm.data.models.requests.DeckCardsRequestDto
import com.example.blackcardskmm.data.models.requests.FractionCardsRequestDto
import com.example.blackcardskmm.domain.models.CardDeckRankGroup
import com.example.blackcardskmm.domain.models.Deck
import com.example.blackcardskmm.domain.models.DeckGroup
import com.example.blackcardskmm.domain.repository.DecksRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class DecksRepositoryImpl(
    private val api: DecksApi,
    private val dispatchers: CustomDispatchers
): KoinComponent, DecksRepository {
    override suspend fun getFractionCards(fractionId: Int): Flow<Result<List<CardDeckRankGroup>>> =
        flow {
            try {
                val data = api.getFractionCards(FractionCardsRequestDto(fractionId = fractionId)).map { CardDeckRankGroup(it) }
                emit(Result.Success(data))
            }
            catch (se: ServerResponseException) {
                val statusCode: Int = se.response.status.value
                emit(Result.Error(code = statusCode, message = se.message))
            } catch (ce: ClientRequestException) {
                val statusCode = ce.response.status.value
                emit(Result.Error(code = statusCode, message = ce.message))
            }
        }.flowOn(dispatchers.default)

    override suspend fun getDeckCards(deckId: Int, fractionId: Int): Flow<Result<List<CardDeckRankGroup>>> =
        flow {
            try {
                val data = api.getDeckCards(DeckCardsRequestDto(deckId, fractionId)).map { CardDeckRankGroup(it) }
                emit(Result.Success(data))
            }
            catch (se: ServerResponseException) {
                val statusCode: Int = se.response.status.value
                emit(Result.Error(code = statusCode, message = se.message))
            } catch (ce: ClientRequestException) {
                val statusCode = ce.response.status.value
                emit(Result.Error(code = statusCode, message = ce.message))
            }
        }.flowOn(dispatchers.default)

    override suspend fun createDeck(request: CreateDeckDto): Flow<Result<Deck>> =
        flow {
            try {
                val data = Deck(api.createDeck(request))
                emit(Result.Success(data))
            }
            catch (se: ServerResponseException) {
                val statusCode: Int = se.response.status.value
                emit(Result.Error(code = statusCode, message = se.message))
            } catch (ce: ClientRequestException) {
                val statusCode = ce.response.status.value
                emit(Result.Error(code = statusCode, message = ce.message))
            }
        }.flowOn(dispatchers.default)

    override suspend fun getDecks(): Flow<Result<List<DeckGroup>>> =
        flow {
            try {
                val data = api.getDecks().map { DeckGroup(it) }
                emit(Result.Success(data))
            }
            catch (se: ServerResponseException) {
                val statusCode: Int = se.response.status.value
                emit(Result.Error(code = statusCode, message = se.message))
            } catch (ce: ClientRequestException) {
                val statusCode = ce.response.status.value
                emit(Result.Error(code = statusCode, message = ce.message))
            }
        }.flowOn(dispatchers.default)
}
package com.example.blackcardskmm.data.repository

import com.example.blackcardskmm.data.api.CardsApi
import com.example.blackcardskmm.data.models.requests.CardsPagingRequestDto
import com.example.blackcardskmm.data.models.requests.SearchPagingRequestDto
import com.example.blackcardskmm.domain.models.CardUnit
import com.example.blackcardskmm.domain.models.CardArt
import com.example.blackcardskmm.domain.models.CardArtDetail
import com.example.blackcardskmm.domain.models.CardSpell
import com.example.blackcardskmm.domain.repository.CardsRepository
import com.example.blackcardskmm.util.Constants
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class CardsRepositoryImpl constructor(
    private val api: CardsApi,
    private val coroutineScope: CoroutineScope,
    private val dispatchers: CustomDispatchers
): KoinComponent, CardsRepository {
    companion object {
        const val INITIAL_KEY = 0
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun getCardArtsAsFlow(filter: String?) : Flow<PagingData<CardArt>> {
        return Pager(
            clientScope = coroutineScope,
            config = PagingConfig(
                pageSize = Constants.CARD_ARTS_PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = INITIAL_KEY,
            getItems = { currentKey, size ->
                val items = api.getCardArts(SearchPagingRequestDto(filter, size, currentKey)).map { CardArt(it) }
                PagingResult(
                    items = items,
                    currentKey = currentKey,
                    prevKey = { if(currentKey == INITIAL_KEY) null else currentKey - 1 },
                    nextKey = { if (items.isEmpty()) null else currentKey + 1 }
                )
            }
        ).pagingData.cachedIn(coroutineScope)
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun getCardUnits(fractionId: Int) : Flow<PagingData<CardUnit>> {
        return Pager(
            clientScope = coroutineScope,
            config = PagingConfig(
                pageSize = Constants.CARD_ARTS_PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = INITIAL_KEY,
            getItems = { currentKey, size ->
                val items = api.getCardUnits(CardsPagingRequestDto(fractionId, size, currentKey)).map { CardUnit(it) }
                PagingResult(
                    items = items,
                    currentKey = currentKey,
                    prevKey = { if(currentKey == INITIAL_KEY) null else currentKey - 1 },
                    nextKey = { if (items.isEmpty()) null else currentKey + 1 }
                )
            }
        ).pagingData.cachedIn(coroutineScope)
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun getCardSpells(fractionId: Int) : Flow<PagingData<CardSpell>> {
        return Pager(
            clientScope = coroutineScope,
            config = PagingConfig(
                pageSize = Constants.CARD_ARTS_PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = INITIAL_KEY,
            getItems = { currentKey, size ->
                val items = api.getCardSpells(CardsPagingRequestDto(fractionId, size, currentKey)).map { CardSpell(it) }
                PagingResult(
                    items = items,
                    currentKey = currentKey,
                    prevKey = { if(currentKey == INITIAL_KEY) null else currentKey - 1 },
                    nextKey = { if (items.isEmpty()) null else currentKey + 1 }
                )
            }
        ).pagingData.cachedIn(coroutineScope)
    }

    override suspend fun getCardArtDetail(id: Int): Flow<Result<CardArtDetail>> =
        flow {
            try {
                val data = api.getCardArtDetail(id)
                emit(Result.Success(CardArtDetail(data)))
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
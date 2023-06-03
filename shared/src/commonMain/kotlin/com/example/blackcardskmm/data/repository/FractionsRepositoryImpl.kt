package com.example.blackcardskmm.data.repository

import com.example.blackcardskmm.data.api.FractionsApi
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class FractionsRepositoryImpl constructor(
    private val api: FractionsApi,
    private val dispatchers: CustomDispatchers
    //private val dao: FractionDao
): KoinComponent, FractionsRepository {
    override suspend fun getFractions(fetchFromRemote: Boolean): Flow<Result<List<Fraction>>> =
        flow {
            try {
                //dao.insertAll(data.map { it.toFractionEntity() })
                val data = api.getFractions().map { Fraction(it) }
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

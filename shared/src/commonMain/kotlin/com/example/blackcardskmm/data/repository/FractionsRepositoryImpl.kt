package com.example.blackcardskmm.data.repository

import com.example.blackcardskmm.data.api.FractionsApi
import com.example.blackcardskmm.data.database.FractionDbObject
import com.example.blackcardskmm.data.database.mappers.toFraction
import com.example.blackcardskmm.data.database.mappers.toFractionDbObject
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.domain.repository.FractionsRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import io.ktor.client.plugins.*
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class FractionsRepositoryImpl constructor(
    private val api: FractionsApi,
    private val dispatchers: CustomDispatchers,
    private val realmDao: Realm
): KoinComponent, FractionsRepository {
    override suspend fun getFractions(fetchFromRemote: Boolean): Flow<Result<List<Fraction>>> =
        flow {
            try {
                var data: List<Fraction>

                if (fetchFromRemote) {
                    data = api.getFractions().map { Fraction(it) }
                    realmDao.write {
                        data.map { copyToRealm(it.toFractionDbObject()) }
                    }
                    emit(Result.Success(data))
                    return@flow
                }

                emit(Result.Success(realmDao.query(FractionDbObject::class).find().map { it.toFraction() }))
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
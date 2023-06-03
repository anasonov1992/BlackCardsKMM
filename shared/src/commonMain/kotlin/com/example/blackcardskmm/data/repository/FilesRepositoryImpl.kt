package com.example.blackcardskmm.data.repository

import com.example.blackcardskmm.data.api.FilesApi
import com.example.blackcardskmm.data.models.requests.SearchRequestDto
import com.example.blackcardskmm.domain.models.LoreFile
import com.example.blackcardskmm.domain.repository.FilesRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class FilesRepositoryImpl(
    private val api: FilesApi,
    private val dispatchers: CustomDispatchers
): KoinComponent, FilesRepository {
    override suspend fun getFiles(request: SearchRequestDto): Flow<Result<List<LoreFile>>> =
        flow {
            try {
                val data = api.getFiles(request).map { LoreFile(it) }
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
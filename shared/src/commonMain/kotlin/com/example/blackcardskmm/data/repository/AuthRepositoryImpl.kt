package com.example.blackcardskmm.data.repository

import com.example.blackcardskmm.data.api.AuthApi
import com.example.blackcardskmm.data.interfaces.AccessTokenStorage
import com.example.blackcardskmm.data.models.requests.LoginRequestDto
import com.example.blackcardskmm.data.models.requests.RegisterRequestDto
import com.example.blackcardskmm.data.models.responses.LoginResponseDto
import com.example.blackcardskmm.data.models.responses.RegisterResponseDto
import com.example.blackcardskmm.domain.repository.AuthRepository
import com.example.blackcardskmm.util.CustomDispatchers
import com.example.blackcardskmm.util.Result
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val storage: AccessTokenStorage,
    private val dispatchers: CustomDispatchers
): KoinComponent, AuthRepository {
    override fun isAuthenticated(): Flow<Result<Boolean>> =
        flow {
            try {
                val token = storage.getAccessToken()
                emit(Result.Success(token.isNotEmpty()))
            }
            catch(ex: RuntimeException){
                Result.Error(message = ex.message ?: ex.stackTraceToString())
            }
        }.flowOn(dispatchers.default)

    override suspend fun login(request: LoginRequestDto): Flow<Result<LoginResponseDto>> =
        flow {
            try {
                val data = api.login(request)
                storage.saveAccessToken(data.token)
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

    override suspend fun register(request: RegisterRequestDto): Flow<Result<RegisterResponseDto>> =
        flow {
            try {
                val data = api.register(request)
                storage.saveAccessToken(data.token)
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
package com.example.blackcardskmm.di

import apiModule
import com.example.blackcardskmm.data.interfaces.AccessTokenStorage
import coreModule
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import platformModule
import repositoryModule

fun appModules(enableNetworkLogs: Boolean = false) = listOf(
    networkModule(enableNetworkLogs),
    apiModule(),
    databaseModule(),
    repositoryModule(),
    coreModule(),
    platformModule()
)

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModules(enableNetworkLogs))
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun networkModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), get(), enableNetworkLogs = enableNetworkLogs) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }


fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    tokenStorage: AccessTokenStorage,
    enableNetworkLogs: Boolean) = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.SIMPLE //TODO: research other loggers - KermitLogger
                level = LogLevel.ALL
            }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens("${tokenStorage.getAccessToken()}", "") //FIXME
                }
            }
        }
        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value

                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response, response.status.description)
                    in 400..499 -> throw ClientRequestException(response, response.status.description)
                    in 500..599 -> throw ServerResponseException(response, response.status.description)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response, response.status.description)
                }
            }

            handleResponseExceptionWithRequest { ex: Throwable, _ ->
                throw ex
            }
        }
    }
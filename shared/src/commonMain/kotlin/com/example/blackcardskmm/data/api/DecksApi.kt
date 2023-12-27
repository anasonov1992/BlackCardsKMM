package com.example.blackcardskmm.data.api

import com.example.blackcardskmm.data.models.CreateDeckDto
import com.example.blackcardskmm.data.models.DeckDto
import com.example.blackcardskmm.data.models.DeckGroupDto
import com.example.blackcardskmm.data.models.DeckRankGroupDto
import com.example.blackcardskmm.data.models.requests.DeckCardsRequestDto
import com.example.blackcardskmm.data.models.requests.FractionCardsRequestDto
import com.example.blackcardskmm.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

class DecksApi(
    private val client: HttpClient,
    private val baseUrl: String = Constants.BASE_URL
): KoinComponent {
    suspend fun getFractionCards(request: FractionCardsRequestDto) = client
        .post("$baseUrl/api/getFractionCards") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .body<List<DeckRankGroupDto>>()

    suspend fun getDeckCards(request: DeckCardsRequestDto) = client
        .post("$baseUrl/api/getDeckCards") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .body<List<DeckRankGroupDto>>()

    suspend fun createDeck(request: CreateDeckDto) = client
        .post("$baseUrl/api/createDeck") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .body<DeckDto>()

    suspend fun getDecks() = client
        .post("$baseUrl/api/getDecks")
        .body<List<DeckGroupDto>>()
}
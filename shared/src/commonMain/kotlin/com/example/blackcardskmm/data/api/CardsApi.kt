package com.example.blackcardskmm.data.api

import com.example.blackcardskmm.data.models.CardArtDetailDto
import com.example.blackcardskmm.data.models.CardArtDto
import com.example.blackcardskmm.data.models.CardSpellDto
import com.example.blackcardskmm.data.models.CardUnitDto
import com.example.blackcardskmm.data.models.requests.CardsPagingRequestDto
import com.example.blackcardskmm.data.models.requests.SearchPagingRequestDto
import com.example.blackcardskmm.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class CardsApi(
    private val client: HttpClient,
    private val baseUrl: String = Constants.BASE_URL
): KoinComponent {
    suspend fun getCardArts(request: SearchPagingRequestDto) = client
        .post("$baseUrl/api/getCardArts") {
            setBody(request)
        }
        .body<List<CardArtDto>>()

    suspend fun getCardArtDetail(id: Int) = client
        .get("$baseUrl/api/getCardArtDetail/$id")
        .body<CardArtDetailDto>()

    suspend fun getCardUnits(request: CardsPagingRequestDto) = client
        .post("$baseUrl/api/getCardUnits") {
            setBody(request)
        }
        .body<List<CardUnitDto>>()

    suspend fun getCardSpells(request: CardsPagingRequestDto) = client
        .post("$baseUrl/api/getCardSpells") {
            setBody(request)
        }
        .body<List<CardSpellDto>>()
}
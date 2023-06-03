package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.CardArtDetailDto

data class CardArtDetail(
    val id: Int = 0,
    val name: String = "Unknown",
    val description: String = "Not found",
    val artUrl: String? = null,
    val fraction: String = "Unknown") {
    constructor(cardArt: CardArtDetailDto) : this(cardArt.id, cardArt.name, cardArt.description, artUrl = cardArt.artUrl, cardArt.fraction)
}

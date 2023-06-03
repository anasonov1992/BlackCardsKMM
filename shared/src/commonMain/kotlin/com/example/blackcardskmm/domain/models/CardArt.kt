package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.CardArtDto

data class CardArt(
    val id: Int = 0,
    val name: String = "Unknown",
    val artUrl: String? = null,
    val fraction: String = "Unknown") {
    constructor(cardArt: CardArtDto) : this(cardArt.id, cardArt.name, cardArt.artUrl, cardArt.fraction)
}

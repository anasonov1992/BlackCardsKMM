package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.DeckDto
import com.example.blackcardskmm.data.models.DeckShortDto
import com.example.blackcardskmm.util.Constants

data class Deck(
    val id: Int,
    val name: String,
    val fractionId: Int,
    val imageUrl: String? = null
) {
    constructor(data: DeckDto): this(
        data.id,
        data.name,
        data.fraction.id,
        data.fraction.logoUrl?.replace("192.168.0.190", Constants.BASE_HOST)) //FIXME

    constructor(data: DeckShortDto): this(data.id, data.name, data.fractionId)
}
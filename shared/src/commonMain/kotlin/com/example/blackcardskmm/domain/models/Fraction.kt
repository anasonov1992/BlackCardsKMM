package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.FractionDto
import com.example.blackcardskmm.data.models.FractionShortDto
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.util.Constants

data class Fraction(
    val id: Int = 0,
    val type: FractionType,
    val name: String = "Unknown",
    val description: String = "Unknown",
    val artUrl: String? = null,
    val logoUrl: String? = null
) {
    constructor(fraction: FractionDto) : this(
        fraction.id,
        fraction.type,
        fraction.name,
        fraction.description,
        fraction.artUrl?.replace("192.168.0.190", Constants.BASE_HOST), //FIXME
        fraction.logoUrl?.replace("192.168.0.190", Constants.BASE_HOST)) //FIXME

    constructor(fraction: FractionShortDto)
            : this(fraction.id, fraction.type, fraction.name, logoUrl = fraction.logoUrl)
}
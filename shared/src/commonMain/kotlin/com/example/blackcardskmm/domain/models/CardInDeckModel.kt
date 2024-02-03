package com.example.blackcardskmm.domain.models

import com.example.blackcardskmm.data.models.CardInDeckDto
import com.example.blackcardskmm.data.primitives.CardUniqueType
import com.example.blackcardskmm.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow

data class CardInDeckModel(
    val id: Int,
    val imageUrl: String? = null,
    val imageFullUrl: String? = null,
    val uniqueType: CardUniqueType = CardUniqueType.Ordinary,
    val amountInDeck: MutableStateFlow<Int> = MutableStateFlow(0)
) {
    constructor(data: CardInDeckDto): this(
        data.id,
        data.imageUrl?.replace("192.168.0.190", Constants.BASE_HOST),     //FIXME
        data.imageFullUrl?.replace("192.168.0.190", Constants.BASE_HOST), //FIXME
        data.uniqueType,
        MutableStateFlow(data.amountInDeck))

    val canAddCardToDeck: Boolean
        get() =
            when(uniqueType) {
                CardUniqueType.Ordinary  -> amountInDeck.value < 3
                CardUniqueType.NotMoreTwo -> amountInDeck.value < 2
                else -> amountInDeck.value < 1
            }

    val canRemoveCardFromDeck: Boolean
        get() = amountInDeck.value > 0

    val canAddCardWarning: String
        get() =
            when(uniqueType) {
                CardUniqueType.Ordinary  -> "Не более трех карт в колоде"
                CardUniqueType.NotMoreTwo -> "Не более двух карт в колоде"
                else -> "Уникальная карта"
            }

    fun amountInDeckUp() = amountInDeck.value++

    fun amountInDeckDown() = amountInDeck.value--
}
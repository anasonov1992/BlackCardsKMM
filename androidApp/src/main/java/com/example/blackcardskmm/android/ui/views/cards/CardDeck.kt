package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.views.cards.components.CardHeader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardDeck(
//    component: CardDeckComponent
) {
//    val state by component.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
//        LazyColumn {
//            state.cardsDeck.forEach { (rankInfo, rankCards) ->
//                stickyHeader(key = rankInfo.rankId) {
//                    CardHeader(title = rankInfo.displayName)
//                }
//                item {
//                    CardsCarousel(
//                        cards = rankCards,
//                        onCardFromDeckRemoved = { rankInfo.cardsOfRankCount.value-- },
//                        onCardToDeckAdded = { rankInfo.cardsOfRankCount.value++ },
//                        canCardBeAdded = { rankInfo.cardsOfRankCount.value < rankInfo.maxCardsOfRankCount },
//                        canCardBeAddedWarning = rankInfo.cardsUpText
//                    )
//                }
//            }
//        }
    }
}
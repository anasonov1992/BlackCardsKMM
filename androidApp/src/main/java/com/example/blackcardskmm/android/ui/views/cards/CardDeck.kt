package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.views.cards.components.CardHeader
import com.example.blackcardskmm.android.ui.vm.CardDeckViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun CardDeck(
    viewModel: CardDeckViewModel = getViewModel(),
    navigator: CommonNavigator
) {
    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn {
            state.cardsDeck.forEach { (rankInfo, rankCards) ->
                stickyHeader(key = rankInfo.rankId) {
                    CardHeader(title = rankInfo.displayName)
                }
                item {
                    CardsCarousel(
                        cards = rankCards,
                        navigator = navigator,
                        onCardFromDeckRemoved = { rankInfo.cardsOfRankCount.value-- },
                        onCardToDeckAdded = { rankInfo.cardsOfRankCount.value++ },
                        canCardBeAdded = { rankInfo.cardsOfRankCount.value < rankInfo.maxCardsOfRankCount },
                        canCardBeAddedWarning = rankInfo.cardsUpText
                    )
                }
            }
        }
    }
}
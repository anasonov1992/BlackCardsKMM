package com.example.blackcardskmm.android.ui.views.cards

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.google.android.material.animation.AnimationUtils
import com.example.blackcardskmm.domain.models.CardInDeckModel
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.ui.views.cards.components.CardPresentation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun CardsCarousel(
    modifier: Modifier = Modifier,
    cards: ImmutableList<CardInDeckModel> = emptyList<CardInDeckModel>().toImmutableList(),
    createDeckModeOn: Boolean = false,
    navigator: CommonNavigator,
    onCardFromDeckRemoved: () -> Unit = { },
    onCardToDeckAdded: () -> Unit = { },
    canCardBeAdded: () -> Boolean = { true },
    canCardBeAddedWarning: String = ""
) {
    val pagerState = rememberPagerState()

//    LazyRow {
//        itemsIndexed(cards, key = { _, model -> model.id } ) { index, _ ->

    HorizontalPager(
        pageCount = cards.count(),
        contentPadding = PaddingValues(horizontal = 32.dp),
        modifier = modifier.fillMaxWidth(),
        state = pagerState,
        userScrollEnabled = true
    ) { page ->
            Card(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                        // We animate the scaleX + scaleY, between 85% and 100%
                        AnimationUtils.lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                            .also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                        alpha = AnimationUtils.lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
            ) {
//            Text(
//                text = "Text Text Text Text Text Text Text Text ",
//                modifier = Modifier.height(460.dp)
//            )
                CardPresentation(
                    modifier = Modifier
                        // We add an offset lambda, to apply a light parallax effect
                        .offset {
                            // Calculate the offset for the current page from the scroll position
                            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                            // Then use it as a multiplier to apply an offset
                            IntOffset(
                                x = (36.dp * pageOffset).roundToPx(),
                                y = 0
                            )
                        },
                    card = cards[page],
                    createDeckModeOn = createDeckModeOn,
                    onCardFromDeckRemoved = onCardFromDeckRemoved,
                    onCardToDeckAdded = onCardToDeckAdded,
                    canCardBeAdded = canCardBeAdded,
                    canCardBeAddedWarning = canCardBeAddedWarning
                )
                {
                    cards[page].imageFullUrl?.let { navigator.navigateEvent(NavigationEvent.CardImageDetail(it)) }
                }
            }
    }
}
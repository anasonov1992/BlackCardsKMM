package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.LoadingIndicator
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.views.cards.components.CardHeader
import com.example.blackcardskmm.components.createDeck.CreateCardDeckComponent
import com.skydoves.balloon.ArrowOrientationRules
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CreateCardDeck(
    component: CreateCardDeckComponent
) {
//    val state by component.state.collectAsStateWithLifecycle()
//
//    val sheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
//        skipHalfExpanded = true
//    )
//
//    val coroutineScope = rememberCoroutineScope()
//
//    val closeBottomSheet = remember {
//        {
//            coroutineScope.launch { sheetState.hide() }
//        }
//    }
//
//    val balloonDismissDuration = 2000L
//
//    val balloonBuilder = rememberBalloonBuilder {
//        setArrowSize(10)
//        setArrowPosition(0.5f)
//        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
//        setArrowOrientationRules(ArrowOrientationRules.ALIGN_ANCHOR)
//        setWidth(BalloonSizeSpec.WRAP)
//        setHeight(BalloonSizeSpec.WRAP)
//        setPadding(12)
//        setCornerRadius(8f)
//        setBackgroundColor(Color.Cyan)
//        setBalloonAnimation(BalloonAnimation.FADE)
//        setAutoDismissDuration(balloonDismissDuration)
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = R.drawable.pergament),
//            contentDescription = "",
//            modifier = Modifier.matchParentSize(),
//            contentScale = ContentScale.Crop
//        )
//        Scaffold(
//            backgroundColor = Color.Transparent,
//            topBar = {
//                TopAppBar(
//                    navigationIcon = {
//                        IconButton(onClick = { navigator.navigateEvent(NavigationEvent.NavigateUp) }) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = "Back",
//                                tint = MaterialTheme.colors.onPrimary
//                            )
//                        }
//                    },
//                    title = {
//                        Text(
//                            text = "Сбор Колоды",
//                            fontFamily = mikadanFont
//                        )
//                    }
//                )
//            },
//            bottomBar = {
//                    BottomAppBar {
//                        Balloon(
//                            builder = balloonBuilder,
//                            balloonContent = {
//                                Text(
//                                    text = state.areCardsUpText,
//                                    color = MaterialTheme.colors.onBackground,
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//                            }
//                        ) { balloonWindow ->
//                            if (state.areCardsUpText.isNotEmpty()) {
//                                balloonWindow.showAlignTop()
//                            }
//                            IconButton(onClick = {
//                                coroutineScope.launch {
//                                    if (sheetState.isVisible)
//                                        sheetState.hide()
//                                    else
//                                        sheetState.show()
//                                }
//                            }) {
//                                BadgedBox(badge = {
//                                    if (state.cardsInDeckCount > 0) {
//                                        Badge {
//                                            Text(text = state.cardsInDeckCount.toString())
//                                        }
//                                    }
//                                }) {
//                                    Icon(
//                                        Icons.Filled.Favorite, //TODO: fix icon
//                                        contentDescription = "Колода"
//                                    )
//                                }
//                            }
//                        }
//                        if (state.cardsInDeckCount == state.maxCardsCount) {
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Button(
//                                onClick = viewModel::createDeck
//                            ) {
//                                Text(
//                                    text = "СОЗДАТЬ КОЛОДУ",
//                                    fontFamily = mikadanFont
//                                )
//                            }
//                        }
//                    }
//            },
//            modifier = Modifier
//        ) { innerPadding ->
//            Box(modifier = Modifier.padding(innerPadding)) {
//                ModalBottomSheetLayout(
//                    sheetState = sheetState,
//                    sheetShape = RoundedCornerShape(16.dp),
//                    sheetContent = {
//                        CardDeckSummaryBottomSheet(
//                            ranksInfo = state.cardsDeck.map { it.rankInfo }.toImmutableList()
//                        )
//                    },
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    LazyColumn {
//                        state.cardsDeck.forEach { (rankInfo, rankCards) ->
//                            stickyHeader(key = rankInfo.rankId) {
//                                CardHeader(title = rankInfo.displayName)
//                            }
//                            item {
//                                CardsCarousel(
//                                    cards = rankCards,
//                                    createDeckModeOn = true,
//                                    navigator = navigator,
//                                    onCardFromDeckRemoved = remember { {
//                                        rankInfo.apply {
//                                            cardsOfRankCount.value--
//                                        }
//                                    } },
//                                    onCardToDeckAdded = remember { {
//                                        rankInfo.apply {
//                                            cardsOfRankCount.value++
//                                            if (areCardsUp) {
//                                                coroutineScope.launch {
//                                                    setCardsUp()
//                                                    delay(balloonDismissDuration)
//                                                    resetCardsUp()
//                                                }
//                                            }
//                                        }
//                                    } },
//                                    canCardBeAdded = remember { {
//                                        rankInfo.cardsOfRankCount.value < rankInfo.maxCardsOfRankCount
//                                    } },
//                                    canCardBeAddedWarning = rankInfo.cardsUpText
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        if (isLoading) {
//            LoadingIndicator(modifier = Modifier.align(Alignment.Center))
//        }
//    }
}
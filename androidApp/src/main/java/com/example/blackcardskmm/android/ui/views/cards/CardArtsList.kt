package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.ErrorItem
import com.example.blackcardskmm.android.ui.components.LoadingItem
import com.example.blackcardskmm.android.ui.components.SearchTextField
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.views.cards.components.CardArtsListItem
import com.example.blackcardskmm.components.cards.CardArtsComponent
import com.example.blackcardskmm.components.cards.CardArtsStore
import com.example.blackcardskmm.domain.models.CardArt
import com.example.blackcardskmm.util.DefaultTitles
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun CardArtsList(
    component: CardArtsComponent
) {
    val state by component.state.collectAsStateWithLifecycle()
    val textSearch by state.textSearch.collectAsState()

    val columnsCount = 2
    val lazyTeams: LazyPagingItems<CardArt> = state.cardArts.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        CollapsingToolbarScaffold(
            state = rememberCollapsingToolbarScaffoldState(),
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Арты",
                            fontFamily = mikadanFont
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            component.onEvent(CardArtsStore.Intent.SearchActivated(!state.isSearchActive))
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                )
            },
            modifier = Modifier
        ) {
            Column {
                AnimatedVisibility(visible = state.isSearchActive) {
                    SearchTextField(
                        label = "Поиск по названию/фракции...",
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onPrimary,
                            fontFamily = mikadanFont
                        ),
                        value = textSearch,
                        onValueChange = { (component::onEvent)(CardArtsStore.Intent.SearchProcessing(filter = it)) },
                        onClear = { (component::onEvent)(CardArtsStore.Intent.SearchCleared) }
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnsCount)
                ) {
                    items(lazyTeams.itemCount, key = { it }) { index ->
                        lazyTeams[index]?.let {
                            CardArtsListItem(it) { artId ->
                                component.onOutput(CardArtsComponent.Output.NavigateToCardArtDetail(artId))
                            }
                        }
                    }
                    lazyTeams.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingItem() }
                                item { LoadingItem() }
                            }
                            loadState.append is LoadState.Loading -> {
                                item { //(GridItemSpan(columnsCount))
                                    LoadingItem()
                                }
                            }
                            loadState.refresh is LoadState.Error -> {
                                val e = lazyTeams.loadState.refresh as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage ?: DefaultTitles.UnknownError,
//                                    modifier = Modifier.fillParentMaxSize(), //FIXME
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                            loadState.append is LoadState.Error -> {
                                val e = lazyTeams.loadState.append as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage ?: DefaultTitles.UnknownError,
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
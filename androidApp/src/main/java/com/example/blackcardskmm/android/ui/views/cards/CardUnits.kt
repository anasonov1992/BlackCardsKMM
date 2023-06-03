package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.CardUnit
import com.example.blackcardskmm.android.ui.components.ErrorItem
import com.example.blackcardskmm.android.ui.components.LoadingItem
import com.example.blackcardskmm.android.ui.states.CardsLibraryState
import com.example.blackcardskmm.util.DefaultTitles

@Composable
fun CardUnits(
    state: CardsLibraryState,
    fractionType: FractionType
) {
    val lazyTeams: LazyPagingItems<CardUnit> = state.cardUnits.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(lazyTeams.itemCount, key = { it }) { index ->
            lazyTeams[index]?.let {
                CardUnitItem(it, fractionType)
            }
        }
        lazyTeams.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyTeams.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage ?: DefaultTitles.UnknownError,
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
package com.example.blackcardskmm.android.ui.views.fractions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.components.fractions.FractionsComponent
import com.example.blackcardskmm.components.fractions.FractionsStore
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun Fractions(
    component: FractionsComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

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
                        Text(text = "Фракции", fontFamily = mikadanFont)
                    }
                )
            },
            modifier = Modifier
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(state.isRefreshing),
                onRefresh = { component.onEvent(FractionsStore.Intent.Refresh) }
            ) {
                LazyColumn {
                    items(state.fractions) {
                        FractionCard(it) { fractionId, fractionType ->
                            component.onOutput(FractionsComponent.Output.NavigateToCardsLibrary(fractionId, fractionType))
                        }
                    }
                }
            }
        }
    }
}
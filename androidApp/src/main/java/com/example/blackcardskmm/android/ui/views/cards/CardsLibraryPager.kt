package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.components.cards.CardsLibraryStore
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun CardsLibraryPager(
    state: CardsLibraryStore.State
) {
    Column {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions
                    )
                )
            }
        ) {
            for ((index, title) in mapOf(0 to "Юниты", 1 to "Заклинания")) {
                Tab(
                    text = {
                        Text(
                            text = title,
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = mikadanFont
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                )
            }
        }
        HorizontalPager(
            pageCount = 2,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { tabIndex ->
            when (tabIndex) {
                0 -> CardUnits(state)
                1 -> CardSpells(state)
            }
        }
    }
}
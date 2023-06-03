package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.ui.states.CardsLibraryState
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.views.navArgs
import com.example.blackcardskmm.android.ui.vm.CardsLibraryViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

data class CardLibraryArgs(
    val fractionId: Int,
    val fractionType: FractionType
)

@Destination(
    navArgsDelegate = CardLibraryArgs::class
)
@Composable
fun CardsLibrary(
    viewModel: CardsLibraryViewModel = getViewModel(),
    navigator: CommonNavigator
) {
    val fractionType = viewModel.savedStateHandle.navArgs<CardLibraryArgs>().fractionType
    val state: CardsLibraryState = viewModel.state

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
                    navigationIcon = {
                        IconButton(onClick = { navigator.navigateEvent(NavigationEvent.NavigateUp) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Библиотека карт",
                            fontFamily = mikadanFont
                        )
                    }
                )
            },
            modifier = Modifier
        ) {
            CardsLibraryPager(state, fractionType)
        }
    }
}
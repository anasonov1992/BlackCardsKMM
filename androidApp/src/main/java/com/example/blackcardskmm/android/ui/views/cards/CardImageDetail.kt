package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.ZoomableImage
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardImageDetail(
    drawerGesturesEnabledState: MutableState<Boolean>
) {
    drawerGesturesEnabledState.value = false

//    val imageUrl = viewModel.savedStateHandle.navArgs<CardImageDetailArgs>().imageUrl
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
//        CollapsingToolbarScaffold(
//            state = scaffoldState,
//            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
//            toolbar = {
//                TopAppBar(
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            drawerGesturesEnabledState.value = true
//                            navigator.navigateEvent(NavigationEvent.NavigateUp)
//                        }) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = "Back",
//                                tint = MaterialTheme.colors.onPrimary
//                            )
//                        }
//                    },
//                    title = {
//                        Text(text = "Black Cards", fontFamily = mikadanFont)
//                    }
//                )
//            },
//            modifier = Modifier
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//            ) {
//                ZoomableImage(
//                    model = imageUrl,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }
    }
}
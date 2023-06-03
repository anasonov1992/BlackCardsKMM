package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.navigation.interfaces.CommonNavigator
import com.example.blackcardskmm.android.ui.navigation.models.NavigationEvent
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.vm.CardArtDetailViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

data class CardArtDetailArgs(
    val id: Int
)

@Destination(
    navArgsDelegate = CardArtDetailArgs::class
)
@Composable
fun CardArtDetail(
    viewModel: CardArtDetailViewModel = getViewModel(),
    navigator: CommonNavigator
) {
    val state = viewModel.state
    val scaffoldState = rememberCollapsingToolbarScaffoldState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        CollapsingToolbarScaffold(
            state = scaffoldState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
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
                        Text(text = state.cardArt.fraction, fontFamily = mikadanFont)
                    }
                )
                Image(
                    contentScale = ContentScale.Crop,
                    painter = // placeholder(R.drawable.ic_user_avatar) //FIXME set actual placeholder
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = state.cardArt.artUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                // placeholder(R.drawable.ic_user_avatar) //FIXME set actual placeholder
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Image",
                    modifier = Modifier
                        .parallax(0.5f)
                        .fillMaxWidth()
                        .height(240.dp)
                        .graphicsLayer {
                            alpha = scaffoldState.toolbarState.progress
                        }
                )
            },
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ConstraintLayout {
                    val (name, description) = createRefs()
                    Text(
                        text = state.cardArt.name,
                        color = MaterialTheme.colors.onSecondary,
                        maxLines = 2,
                        fontSize = 20.sp, //FIXME set textStyle from theme
                        fontWeight = FontWeight.Bold,
                        fontFamily = mikadanFont,
                        modifier = Modifier
                            .constrainAs(name) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    Text(
                        text = state.cardArt.description,
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = 16.sp, //FIXME set textStyle from theme
                        fontFamily = mikadanFont,
                        modifier = Modifier
                            .constrainAs(description) {
                                top.linkTo(name.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }
}
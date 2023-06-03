package com.example.blackcardskmm.android.ui.views.cards.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.example.blackcardskmm.domain.models.CardInDeckModel
import com.example.blackcardskmm.android.ui.components.LoadingIndicator
import com.example.blackcardskmm.android.ui.components.RoundedButton
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.theme.CompleteBackgroundColor
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardPresentation(
    modifier: Modifier = Modifier,
    card: CardInDeckModel,
    createDeckModeOn: Boolean,
    onCardFromDeckRemoved: () -> Unit = { },
    onCardToDeckAdded: () -> Unit = { },
    canCardBeAdded: () -> Boolean = { true },
    canCardBeAddedWarning: String = "",
    onClick: (Int) -> Unit = { }
) {
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidthRatio(0.55f)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(12)
        setMarginHorizontal(12)
        setCornerRadius(8f)
        setBackgroundColor(Color.Cyan)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
    }

    var rotated by rememberSaveable { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(460.dp)
                .fillMaxWidth()
                .padding(10.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        rotated = !rotated
                    }
                )
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onLongPress = { onClick(card.id) }
//                    )
//                }
        ) {
            if (rotated) {
                SubcomposeAsyncImage(
                    model = R.drawable.cover_template,
                    contentDescription = "Cover",
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .height(460.dp)
                        .graphicsLayer {
                            alpha = animateBack
                        },
                )
            } else {
                SubcomposeAsyncImage(
                    model = card.imageUrl ?: R.drawable.knight_card_template,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            LoadingIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    },
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .height(460.dp)
                        .graphicsLayer {
                            alpha = animateFront
                        }
                )
            }
        }

        if (createDeckModeOn) {
            Row {
                RoundedButton(
                    enabled = card.amountInDeck.value > 0,
                    onClick = {
                        if (card.canRemoveCardFromDeck) {
                            card.amountInDeck.value--
                            onCardFromDeckRemoved()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                RoundedButton(
                    backgroundColor = if (card.amountInDeck.value > 0) CompleteBackgroundColor else MaterialTheme.colors.primary
                ) {
                    Text(
                        text = card.amountInDeck.value.toString(),
                        style = TextStyle(
                            color = if (card.amountInDeck.value > 0) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = mikadanFont
                        )
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Balloon(
                    builder = builder,
                    balloonContent = {
                        Text(
                            text = if (!canCardBeAdded()) canCardBeAddedWarning else card.canAddCardWarning,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                ) { balloonWindow ->
                    val canCardBeAdded = canCardBeAdded() && card.canAddCardToDeck
                    RoundedButton(
                        enabled = canCardBeAdded,
                        onClick = {
                            if (!canCardBeAdded) {
                                balloonWindow.showAlignTop()
                            }
                            else {
                                card.amountInDeck.value++
                                onCardToDeckAdded()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.CardUnit
import com.example.blackcardskmm.android.ui.components.HtmlText
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.theme.vinqueFont
import com.example.blackcardskmm.android.util.buildUnitClassesString
import com.example.blackcardskmm.android.util.getTextColorByFractionType
import com.example.blackcardskmm.android.util.getTextColorByUniqueness

@Composable
fun CardUnitItem(
    card: CardUnit,
    fractionType: FractionType,
    onClick: (Int) -> Unit = { }
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(240.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        ConstraintLayout {
            val (image, rank, name, classesText, uniquenessText, flavor, description) = createRefs()
            Image(
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = card.imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Image",
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(112.dp)
                    .padding(8.dp)
            )
            // Rank
            Text(
                text = card.rankText,
                style = TextStyle(
                    color = getTextColorByFractionType(fractionType),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(rank) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                    }
                    .wrapContentSize()
                    .padding(start = 8.dp, top = 16.dp)
            )
            // Name
            Text(
                text = card.name,
                maxLines = 2,
                style = TextStyle(
                    color = getTextColorByFractionType(fractionType),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(rank.end)
                    }
                    .wrapContentSize()
                    .padding(start = 8.dp, top = 16.dp, end = 16.dp)
            )
            // Unit classes
            Text(
                text = buildUnitClassesString(card.classes),
                maxLines = 2,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 14.sp,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(classesText) {
                        top.linkTo(rank.bottom)
                        start.linkTo(image.end)
                    }
                    .wrapContentSize()
                    .padding(start = 8.dp, top = 6.dp, end = 16.dp)
            )

            // IsUnigue
            card.uniquenessText?.let {
                Text(
                    text = card.uniquenessText ?: "",
                    maxLines = 2,
                    style = TextStyle(
                        color = getTextColorByUniqueness(card.uniqueType),
                        fontSize = 14.sp,
                        fontFamily = vinqueFont
                    ), //FIXME set textStyle from theme
                    modifier = Modifier
                        .constrainAs(uniquenessText) {
                            top.linkTo(classesText.bottom)
                            start.linkTo(image.end)
                        }
                        .wrapContentSize()
                        .padding(start = 8.dp, top = 6.dp, end = 16.dp)
                )
            }
            // Description
            HtmlText(
                html = card.description,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 12.sp,
                    fontFamily = mikadanFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 12.dp, end = 16.dp)
            )
            // Flavor
            Text(
                text = card.flavor,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(flavor) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 12.dp, top = 6.dp, end = 16.dp, bottom = 12.dp)
            )
        }
    }
}
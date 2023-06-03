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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.CardSpell
import com.example.blackcardskmm.android.ui.components.HtmlText
import com.example.blackcardskmm.android.ui.theme.vinqueFont
import com.example.blackcardskmm.android.util.buildSpellTypesString
import com.example.blackcardskmm.android.util.getTextColorByFractionType

@Composable
fun CardSpellItem(
    card: CardSpell,
    fractionType: FractionType,
    onClick: (Int) -> Unit = { }
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(280.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        ConstraintLayout {
            val (image, name, typesText, flavor, description) = createRefs()
            Image(
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(
                    data = card.imageUrl,
                    builder = {
//                      placeholder(R.drawable.ic_user_avatar) //FIXME set actual placeholder
                        crossfade(true)
                    }
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
            // Name
            Text(
                text = card.name,
                maxLines = 2,
                style = TextStyle(
                    color = getTextColorByFractionType(fractionType),
                    fontSize = 16.sp,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                    }
                    .wrapContentSize()
                    .padding(start = 8.dp, top = 24.dp, end = 16.dp)
            )
            // Spell types
            Text(
                text = buildSpellTypesString(card.types),
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(typesText) {
                        top.linkTo(name.bottom)
                        start.linkTo(image.end)
                    }
                    .wrapContentSize()
                    .padding(start = 8.dp, top = 6.dp, end = 16.dp)
            )
            // Description
            HtmlText(
                html = card.description,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 12.sp,
                    fontFamily = vinqueFont
                ), //FIXME set textStyle from theme
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 12.dp, end = 16.dp)
            )
            HtmlText(
                html = card.flavor,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 12.sp,
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
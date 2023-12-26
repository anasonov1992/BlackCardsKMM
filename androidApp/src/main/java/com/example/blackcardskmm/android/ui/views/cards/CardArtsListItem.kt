package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.blackcardskmm.domain.models.CardArt
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
fun CardArtsListItem(
    cardArt: CardArt,
    onItemClick: (Int) -> Unit
) {
    Card(
        elevation = 20.dp,
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(260.dp)
            .fillMaxWidth()
            .clickable { onItemClick.invoke(cardArt.id) }
    ) {
        ConstraintLayout {
            val (image, name, fraction) = createRefs()
            Image(
                contentScale = ContentScale.Crop,
                painter = //                      placeholder(R.drawable.ic_user_avatar) //FIXME set actual placeholder
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = cardArt.artUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            //                      placeholder(R.drawable.ic_user_avatar) //FIXME set actual placeholder
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Image",
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(
                text = cardArt.name,
                color = MaterialTheme.colors.onSecondary,
                maxLines = 2,
                fontSize = 20.sp, //FIXME set textStyle from theme
                fontWeight = FontWeight.Bold,
                fontFamily = mikadanFont,
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                text = cardArt.fraction,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 2,
                fontSize = 16.sp, //FIXME set textStyle from theme
                fontWeight = FontWeight.Bold,
                fontFamily = mikadanFont,
                modifier = Modifier
                    .constrainAs(fraction) {
                        top.linkTo(name.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
    }
}
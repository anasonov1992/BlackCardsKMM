package com.example.blackcardskmm.android.ui.views.fractions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
fun ShortFractionCard(
    fraction: Fraction,
    onClick: (Int) -> Unit = { _ -> }
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick.invoke(fraction.id) }
    ) {
        SubcomposeAsyncImage(
            model = fraction.logoUrl ?: R.drawable.bc_logo,
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = fraction.name,
            color = MaterialTheme.colors.primary,
            maxLines = 3,
            fontSize = 20.sp, //FIXME set textStyle from theme
            fontWeight = FontWeight.Bold,
            fontFamily = mikadanFont
        )
    }
}

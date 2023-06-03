package com.example.blackcardskmm.android.ui.views.fractions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.data.primitives.FractionType
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.android.ui.components.RoundedImage
import com.example.blackcardskmm.util.DefaultTitles

@Composable
fun FractionCard(
    fraction: Fraction? = null,
    modifier: Modifier = Modifier,
    onClick: (Int, FractionType) -> Unit = { _: Int, _: FractionType -> }
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        RoundedImage(
            width = 350.dp,
            height = 200.dp,
            imageUrl = fraction?.artUrl,
            corners = 16.0f,
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { fraction?.let { onClick.invoke(it.id, it.type) } }
//            hasLoadingPlaceholder = state.isLoading
        )
        Text(
            text = fraction?.name ?: DefaultTitles.UnknownText,
            color = MaterialTheme.colors.onPrimary,
            maxLines = 2,
            fontSize = 20.sp, //FIXME set textStyle from theme
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}
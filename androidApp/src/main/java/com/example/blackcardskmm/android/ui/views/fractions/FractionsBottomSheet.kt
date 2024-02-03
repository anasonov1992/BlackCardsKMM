package com.example.blackcardskmm.android.ui.views.fractions

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.domain.models.Fraction
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FractionsBottomSheet(
    fractions: ImmutableList<Fraction>,
    closeBottomSheet: (Int) -> Unit = { _ -> }
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Выберите фракцию",
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = mikadanFont,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        fractions.forEach {
            ShortFractionCard(
                fraction = it,
                onClick = closeBottomSheet
            )
        }
    }
}
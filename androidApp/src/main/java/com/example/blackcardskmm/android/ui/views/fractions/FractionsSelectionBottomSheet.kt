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
import com.example.blackcardskmm.android.ui.components.ActionButton
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.domain.models.FractionSelectionModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FractionsSelectionBottomSheet(
    fractions: ImmutableList<FractionSelectionModel>,
    onApplyFilters: () -> Unit = { }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Выберите фракции",
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = mikadanFont,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        fractions.forEach {
            ShortFractionSelectionCard(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ActionButton(
            "Применить",
            onClick = onApplyFilters,
            modifier = Modifier.width(240.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}
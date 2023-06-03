package com.example.blackcardskmm.android.ui.views.fractions

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blackcardskmm.android.ui.components.ActionButton
import com.example.blackcardskmm.domain.models.FractionSelectionModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FractionsSelectionBottomSheet(
    fractions: ImmutableList<FractionSelectionModel>,
    onApplyFilters: () -> Unit = { }
) {
    Column {
        fractions.forEach {
            ShortFractionSelectionCard(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ActionButton(
            "Применить",
            onClick = onApplyFilters,
            modifier = Modifier.width(240.dp)
        )
    }
}

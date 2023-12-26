package com.example.blackcardskmm.android.ui.views.fractions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.domain.models.FractionSelectionModel

@Composable
fun ShortFractionSelectionCard(
    fraction: FractionSelectionModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))

        val isSelected by fraction.isSelected.collectAsStateWithLifecycle()

        Checkbox(
            checked = isSelected,
            onCheckedChange = { fraction.select() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                checkmarkColor = MaterialTheme.colors.onPrimary
            )
        )
    }
}

package com.example.blackcardskmm.android.ui.views.decks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.domain.models.Deck
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@Composable
fun ShortDeckCard(
    deck: Deck,
    onClick: (Int, Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick.invoke(deck.fractionId, deck.id) }
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = deck.name,
            color = MaterialTheme.colors.primary,
            maxLines = 3,
            fontSize = 20.sp, //FIXME set textStyle from theme
            fontWeight = FontWeight.Bold,
            fontFamily = mikadanFont
        )
    }
}
package com.example.blackcardskmm.android.ui.views.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.components.RoundedButton
import com.example.blackcardskmm.android.ui.theme.CompleteBackgroundColor
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.theme.vinqueFont
import com.example.blackcardskmm.domain.models.CardDeckRankInfo
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CardDeckSummaryBottomSheet(
    ranksInfo: ImmutableList<CardDeckRankInfo>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        ranksInfo.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(24.dp))
                Row(modifier = Modifier.weight(1f)) {
                    Text(
                        text = it.rankName,
                        style = TextStyle(
                            color = MaterialTheme.colors.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = vinqueFont
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedButton(
                        backgroundColor = if (it.cardsOfRankCount.value > 0) CompleteBackgroundColor else MaterialTheme.colors.primary
                    ) {
                        Text(
                            text = it.cardsOfRankCount.value.toString(),
                            style = TextStyle(
                                color = if (it.cardsOfRankCount.value > 0) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = mikadanFont
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "из",
                        style = TextStyle(
                            color = MaterialTheme.colors.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = vinqueFont
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    RoundedButton {
                        Text(
                            text = it.maxCardsOfRankCount.toString(),
                            style = TextStyle(
                                color = MaterialTheme.colors.onPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = mikadanFont
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
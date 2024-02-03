package com.example.blackcardskmm.android.ui.views.lore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.blackcardskmm.domain.models.LoreFile
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.R

@Composable
fun LoreFilesListItem(
    file: LoreFile,
    downloadFile: (LoreFile) -> Unit,
    openFile: (LoreFile) -> Unit
) {
    Card(
        elevation = 20.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                if (file.downloadedUri.value.isNullOrEmpty()) {
                    downloadFile(file)
                } else {
                    openFile(file)
                }
            }
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            SubcomposeAsyncImage(
                model = file.imageUrl ?: R.drawable.bc_logo,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center)
                        )
                    }
                },
                contentDescription = "File",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(112.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = file.fullName,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 16.sp, //FIXME set textStyle from theme
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = mikadanFont,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
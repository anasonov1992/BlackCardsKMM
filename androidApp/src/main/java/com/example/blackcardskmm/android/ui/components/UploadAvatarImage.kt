package com.example.blackcardskmm.android.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import java.io.File
import java.io.InputStream

@Composable
fun UploadAvatarImage(
    size: Dp,
    avatarUrl: String? = null,
    modifier: Modifier = Modifier,
    onImageSelected: (InputStream, File) -> Unit = { _, _ -> }
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .size(size)
            .background(Color.Gray),
        onClick = { launcher.launch("image/*") })
    {
        imageUri?.let { uri -> run {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val optionsJustBounds = BitmapFactory.Options()
                optionsJustBounds.inJustDecodeBounds = true
                BitmapFactory.decodeStream(stream, null, optionsJustBounds)
                val file = File.createTempFile(
                    "avatar_${System.currentTimeMillis()}",
                    ".jpg",
                    context.cacheDir)
                onImageSelected(stream, file)
            }
        }
            AvatarImage(
                size = size,
                avatarUrl = avatarUrl
            )
        }
    }
}





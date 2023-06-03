package com.example.blackcardskmm.android.ui.components

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.example.blackcardskmm.android.R
import kotlin.math.max

@Composable
fun HtmlText(
    html: String,
    textStyle: TextStyle = LocalTextStyle.current,
    modifier: Modifier = Modifier
) {
    fun spToPx(sp: Int, context: Context): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), context.resources.displayMetrics)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val spacingReady = max(textStyle.lineHeight.value - textStyle.fontSize.value, 0f)
            val extraSpacing = spToPx(spacingReady.toInt(), context)
            val gravity = when (textStyle.textAlign) {
                TextAlign.Center -> Gravity.CENTER
                TextAlign.End -> Gravity.END
                else -> Gravity.START
            }
            val fontResId = R.font.vinque_rg
            val font = ResourcesCompat.getFont(context, fontResId)

            TextView(context).apply {
                textSize = textStyle.fontSize.value
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
                typeface = font
                setTextColor(textStyle.color.toArgb())
                setLineSpacing(extraSpacing, 1f)
                setGravity(gravity)
            }
        }
    )
}
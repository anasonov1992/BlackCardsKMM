package com.example.blackcardskmm.android.ui.views.decks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackcardskmm.android.ui.components.ActionButton
import com.example.blackcardskmm.android.ui.components.CustomTextField
import com.example.blackcardskmm.android.ui.components.HtmlText
import com.example.blackcardskmm.android.ui.theme.mikadanFont

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetDeckNameBottomSheet(
    closeBottomSheet: (String?) -> Unit = { _ -> }
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var deckName by rememberSaveable { mutableStateOf("") }

    val disclaimer = "В каждой колоде изначально должно быть <span style='color:#1e9713'><b>57 карт.</b></span> Вы можете иметь строго определенное число карт разной силы:<br /><br />" +
            "<span style='color:#1e9713'><b>(8) – Король. Сильнейшая карта колоды,</b></span> способная перевернуть игру одним своим появлением. Разыгранный Король уничтожается по окончании раунда. В колоде <span style='color:#1e9713'><b>1</b></span> карта силой <span style='color:#1e9713'><b>8</b></span>.<br />" +
            "<span style='color:#1e9713'><b>(7) – Ключевые карты в колоде</b></span> и одни из самых эффективных при розыгрыше. В колоде <span style='color:#1e9713'><b>3</b></span> карты силой <span style='color:#1e9713'><b>7</b></span>.<br />" +
            "<span style='color:#1e9713'><b>(6) – Карты с сильными способностями,</b></span> как правило, самодостаточные, с уникальными свойствами. В колоде <span style='color:#1e9713'><b>4</b></span> карты силой <span style='color:#1e9713'><b>6</b></span>.<br />" +
            "<span style='color:#1e9713'><b>(5) – Ударная сила фракции,</b></span> совместно с картами силой <span style='color:#1e9713'><b>4</b></span>, они представляют собой основной боевой ресурс колоды. В колоде <span style='color:#1e9713'><b>6</b></span> карт силой <span style='color:#1e9713'><b>5</b></span>.<br />" +
            "<span style='color:#1e9713'><b>(от 1 до 4) – Костяк Вашей колоды.</b></span> Армия, которой суждено побеждать. В колоде:<br />" +
            "<span style='color:#1e9713'><b>8</b></span> карт силой <span style='color:#1e9713'><b>4</b></span><br />" +
            "<span style='color:#1e9713'><b>8</b></span> карт силой <span style='color:#1e9713'><b>3</b></span><br />" +
            "<span style='color:#1e9713'><b>8</b></span> карт силой <span style='color:#1e9713'><b>2</b></span><br />" +
            "<span style='color:#1e9713'><b>6</b></span> карт силой <span style='color:#1e9713'><b>1</b></span><br />" +
            "<span style='color:#1e9713'><b>6</b></span> карт <span style='color:#1e9713'><b>Заклинаний</b></span><br />" +
            "<span style='color:#1e9713'><b>4</b></span> карты <span style='color:#1e9713'><b>Снаряжения</b></span><br />" +
            "<span style='color:#1e9713'><b>1</b></span> карта <span style='color:#1e9713'><b>Элитного Снаряжения</b></span><br />"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Соберите колоду",
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = mikadanFont,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = deckName,
            onValueChange = { deckName = it },
            label = "Название:"
        )
        if (deckName.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                "Собрать",
                onClick = {
                    keyboardController?.hide()
                    closeBottomSheet(deckName)
                },
                modifier = Modifier.width(240.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        HtmlText(
            html = disclaimer,
            textStyle = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = mikadanFont
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
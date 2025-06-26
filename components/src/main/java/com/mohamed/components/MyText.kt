package com.mohamed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun MyText(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = Constants.DEFAULT_APP_TEXT_SIZE,
    padding: Dp = Constants.DEFAULT_PADDING,
    textColor: Color = Color.Black,
) {
    Text(
        text = text,
        fontSize = textSize,
        fontWeight = FontWeight.Medium,
        color = textColor,
        modifier = modifier
            .padding(all = padding)
    )
}
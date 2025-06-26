package com.mohamed.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun MyButton(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = Constants.DEFAULT_APP_TEXT_SIZE,
    padding: Dp = Constants.DEFAULT_PADDING,
    borderStroke: Dp = Constants.DEFAULT_BORDER_STROCK,
    borderColor: Color = Constants.DEFAULT_BORDER_COLOR,
    onClickAction: () -> Unit
) {
    Button(
        onClick = onClickAction,
        modifier = modifier,
        content = {
            MyText(text, modifier, textSize, padding)
        },
        border = BorderStroke(width = borderStroke, color = borderColor),
    )
}
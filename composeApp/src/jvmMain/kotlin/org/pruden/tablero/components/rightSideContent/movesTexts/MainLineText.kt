package org.pruden.tablero.components.rightSideContent.movesTexts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainLineText(
    text: String,
    modifier: Modifier = Modifier,
    hoverColor: Color,
    textColor: Color,
    padding: PaddingValues = PaddingValues(all = 0.dp),
    isThisMove: Boolean = false,
    onClick: () -> Unit = { },
    fontSize: TextUnit = 20.sp
) {
    val isHovered = remember { mutableStateOf(false) }
    Text(
        text = text,
        modifier = modifier
            .background(if (isHovered.value && text.isNotEmpty()) hoverColor else if(isThisMove) hoverColor.copy(0.5f) else Color.Transparent)
            .padding(padding)
            .pointerMoveFilter(
                onEnter = {
                    isHovered.value = true
                    false
                },
                onExit = {
                    isHovered.value = false
                    false
                }
            ).clickable {
                onClick()
            },
        color = textColor,
        fontWeight = if (isThisMove) FontWeight.Bold else FontWeight.Normal,
        fontSize = fontSize
    )
}
package org.pruden.tablero.components.rightSideContent.movesTexts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VariantText(
    move: MoveNode,
    //modifier: Modifier,
    color: Color = Colors.movesColor,
    hoverColor: Color = Colors.hoverColor,
    onClick: () -> Unit = {}
){
    val parentMove = Globals.movesNodesBuffer.value.find { it.id == move.parentId }!!
    val isFirstMoveOfVariant = parentMove.childrenIds.indexOf(move.id) == 0
    val text = "${if(move.isWhiteMove!!) move.getStepsFromRoot().toString() + "." else if(isFirstMoveOfVariant) "" else move.getStepsFromRoot().toString()+"..."} ${move.san!!} "

    val isHovered = remember { mutableStateOf(false) }

    val isThisMove = move.isActualMove

    Text(
        text = text,
        fontSize = 16.sp,
        color = color,
        modifier = Modifier
            .background(if(isHovered.value && text.isNotEmpty()) hoverColor else if(isThisMove) hoverColor.copy(0.5f) else Color.Transparent)
            .pointerMoveFilter (
                onEnter = {
                    isHovered.value = true
                    false
                },
                onExit = {
                    isHovered.value = false
                    false
                }
            )
            .clickable {
                onClick()
            },
        fontWeight = if (isThisMove) FontWeight.Bold else FontWeight.Normal,
    )
}
package org.pruden.tablero.components.rightSideContent.movesTexts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode

@Composable
fun VariantText(
    move: MoveNode,
    //modifier: Modifier,
    color: Color = Colors.movesColor
){
    val parentMove = Globals.movesNodesBuffer.value.find { it.id == move.parentId }!!

    val isFirstMoveOfVariant = parentMove.childrenIds.indexOf(move.id) == 0


    val text = "${if(move.isWhiteMove!!) move.getStepsFromRoot().toString() + "." else if(isFirstMoveOfVariant) "" else move.getStepsFromRoot().toString()+"..."} ${move.san!!} "


    Text(
        text = text,
        fontSize = 16.sp,
        color = color

    )
}
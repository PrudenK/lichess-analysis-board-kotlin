package org.pruden.tablero.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import androidx.compose.ui.graphics.graphicsLayer
import org.pruden.tablero.utils.promotion.PromotionHandler

@Composable
fun Cell(
    columnX: Int,
    rowY: Int,
    cell: BoxModel,
    onClick: (Int, Int) -> Unit
) {
    Box(
        Modifier
            .size(Globals.BOX_SIZE.dp)
            .background(cell.color!!)
            .border(0.5.dp, Color.Black.copy(alpha = 0.5f))
            .clickable {
                if(!cell.disable){
                    onClick(columnX, rowY)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { pos ->
                        println(cell.pieceOnBox)

                        if (!cell.disable && cell.pieceOnBox != null &&
                            (Globals.isWhiteMove.value && cell.pieceOnBox?.color == org.pruden.tablero.models.Color.White ||
                                    !Globals.isWhiteMove.value && cell.pieceOnBox?.color == org.pruden.tablero.models.Color.Black)
                            ) {
                            onClick(columnX, rowY)
                            Globals.isDragging.value = true
                            Globals.dragPng.value = cell.pieceOnBox?.png
                            val cs = Globals.cellSizePx.value
                            Globals.dragPointerPx.value = Offset(columnX * cs + pos.x, rowY * cs + pos.y)
                        }
                    },
                    onDrag = { change, _ ->
                        if (!cell.disable && Globals.isDragging.value) {
                            val cs = Globals.cellSizePx.value
                            Globals.dragPointerPx.value = Offset(
                                columnX * cs + change.position.x,
                                rowY * cs + change.position.y
                            )
                        }
                    },
                    onDragEnd = {
                        if (Globals.isDragging.value) {
                            val cs = Globals.cellSizePx.value
                            val p = Globals.dragPointerPx.value
                            val dstCol = (p.x / cs).toInt().coerceIn(0, Globals.BOX_WIDTH - 1)
                            val dstRow = (p.y / cs).toInt().coerceIn(0, Globals.BOX_HEIGHT - 1)
                            Globals.isDragging.value = false
                            Globals.dragPng.value = null
                            onClick(dstCol, dstRow)
                        }
                    },
                    onDragCancel = {
                        Globals.isDragging.value = false
                        Globals.dragPng.value = null
                    }
                )
            }
    ) {
        PromotionHandler.applyPromotionFilter()

        // Pieza seleccionada
        if (cell.pieceOnBox?.isSelected?.value == true) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f))
            )
        }


        // Anterior movimiento
        val actualMove = Globals.movesBufferNotation.value.find { it.isActualMove }
        if(actualMove != null){
            val lm = actualMove.toLastMove()
            val highlight = ((columnX == lm.from.first && rowY == lm.from.second) ||
                            (columnX == lm.to.first && rowY == lm.to.second))

            if (highlight) {
                Box(Modifier.fillMaxSize().background(Color.Green.copy(alpha = 0.2f)))
            }
        }


        cell.pieceOnBox?.let {
            Image(
                painter = painterResource(resource = it.png!!),
                contentDescription = null,
                modifier = Modifier.scale(1.1f).fillMaxSize().padding(6.dp)
                    .graphicsLayer(alpha = if(it.isSelected.value) 0.5f else 1.0f)
                    .rotate(if(Globals.isBoardRotated.value) 180f else 0f)
            )
        }

        if (Globals.possibleMoves.value.any { it.first == columnX && it.second == rowY }) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(Color.Green, shape = CircleShape)
            )
        }





        // Check color
        val isCheckedKingCell = Globals.checkedKingPos.value?.let { it.first == columnX && it.second == rowY } == true

        if (isCheckedKingCell) {
            Box(Modifier.fillMaxSize().background(Color.Red.copy(alpha = 0.4f)))
        }
    }
}
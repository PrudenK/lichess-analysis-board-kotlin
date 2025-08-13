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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun Cell(
    columnX: Int,
    rowY: Int,
    cell: BoxModel,
    onClick: (Int, Int) -> Unit,
    onDrag: (Int, Int, Offset) -> Unit,
    onDrop: (Int, Int) -> Unit
) {
    Box(
        Modifier
            .size(Globals.BOX_SIZE.dp)
            .background(cell.color!!)
            .border(0.5.dp, Color.Black.copy(alpha = 0.5f))
            .clickable { onClick(columnX, rowY) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        // Inicio del arrastre
                        onClick(columnX, rowY) // opcional: seleccionar pieza
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        // Movimiento mientras arrastras
                        onDrag(columnX, rowY, dragAmount)
                    },
                    onDragEnd = {
                        // Soltaste la pieza
                        onDrop(columnX, rowY)
                    },
                    onDragCancel = {
                        // Cancelación (fuera del área, etc.)
                    }
                )
            }
    ) {


        if (cell.pieceOnBox?.isSelected?.value == true) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green.copy(alpha = 0.5f))
            )
        }

        cell.pieceOnBox?.let {
            Image(
                painter = painterResource(resource = it.png!!),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(6.dp)
                    .graphicsLayer(alpha = if(it.isSelected.value) 0.5f else 1.0f)
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
    }
}
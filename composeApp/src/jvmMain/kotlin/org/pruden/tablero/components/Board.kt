package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.MoveCalculator
import org.pruden.tablero.utils.loadChessBoard


@Composable
fun ChessBoard(
    modifier: Modifier,
) {
    loadChessBoard()

    Box(
        modifier = modifier.padding(50.dp).background(Color.Black.copy(alpha = 0.6f)),
    ){
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp).background(Color.White),
        ){
            PaintChessBoardBoxes()

        }
    }
}

@Composable
fun PaintChessBoardBoxes() {
    Column(
        modifier = Modifier.padding(30.dp).border(4.dp, Color.Black).padding(4.dp),
    ) {
        repeat(Globals.BOX_HEIGHT) { rowY ->
            Row {
                repeat(Globals.BOX_WIDTH) { columnX ->
                    Cell(
                        columnX = columnX,
                        rowY = rowY,
                        cell = Globals.chessBoard[rowY][columnX],
                        onClick = { clickedCol, clickedRow ->
                            var moveWasDone = false

                            // Realizar movimiento
                            if (Globals.possibleMoves.value.contains(Pair(clickedCol, clickedRow))) {
                                val selectedPiecePos = Globals.chessBoard
                                    .flatMapIndexed { rowIndex, row ->
                                        row.mapIndexedNotNull { colIndex, box ->
                                            if (box.pieceOnBox?.isSelected?.value == true) Pair(colIndex, rowIndex) else null
                                        }
                                    }
                                    .firstOrNull()

                                if (selectedPiecePos != null) {
                                    moveWasDone = true
                                    val (selCol, selRow) = selectedPiecePos
                                    Globals.chessBoard[clickedRow][clickedCol].pieceOnBox = Globals.chessBoard[selRow][selCol].pieceOnBox
                                    Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.position = Pair(clickedCol, clickedRow)
                                    Globals.chessBoard[selRow][selCol].pieceOnBox = null
                                }
                            }

                            //Quitar selección
                            Globals.possibleMoves.value = emptyList()
                            Globals.chessBoard.forEach { row ->
                                row.forEach { box ->
                                    box.pieceOnBox?.isSelected?.value = false
                                }
                            }




                            // Seleccionar pieza si hay una en la casilla clicada
                            if(!moveWasDone) {
                                if (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox != null) {
                                    Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.isSelected.value = true
                                    Globals.possibleMoves.value = MoveCalculator.getPosibleMoves(
                                        Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!
                                    )
                                }
                            }
                        },
                        onDrag = { x, y, offSet ->

                        },
                        onDrop = { x, y ->

                        }
                    )
                }
            }
        }
    }
}




package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.MoveCalculator
import org.pruden.tablero.utils.loadChessBoard


@Composable
fun ChessBoard(
    modifier: Modifier,
) {
    if (!Globals.isBoardLoaded.value) {
        loadChessBoard()
        Globals.isBoardLoaded.value = true
    }

    Box(
        modifier = modifier
            .padding(50.dp)
            .background(Color.Black.copy(alpha = 0.6f)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.White),
        ) {
            Row {
                PaintChessBoardBoxes()

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Juegan ${if (Globals.isWhiteMove.value) "Blancas" else "Negras"}",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 24.dp),
                    )
                }
            }
        }
    }
}


@Composable
fun PaintChessBoardBoxes() {
    if(Globals.isBoardLoaded.value) {
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

                                        Globals.isWhiteMove.value = !Globals.isWhiteMove.value

                                        // Castle
                                        val movedPiece = Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!
                                        if(movedPiece.color == org.pruden.tablero.models.Color.White) {
                                            if(movedPiece.type == PieceType.King) {
                                                Globals.whiteCastle = Triple(false, true, false)
                                            }else if(movedPiece.type == PieceType.Rook) {

                                                Globals.whiteCastle = Triple(movedPiece.id == 24, Globals.whiteCastle.second, movedPiece.id == 31)
                                            }
                                        }else{
                                            if(movedPiece.type == PieceType.King) {
                                                Globals.blackCastle = Triple(false, true, false)
                                            }else if(movedPiece.type == PieceType.Rook) {
                                                Globals.blackCastle = Triple(movedPiece.id == 0, Globals.blackCastle.second, movedPiece.id == 7)
                                            }
                                        }

                                        if(movedPiece.type == PieceType.King){
                                            val startPos = Globals.chessBoard[selRow][selCol].boxNotation
                                            val endPos = Globals.chessBoard[clickedRow][clickedCol].boxNotation
                                            if(movedPiece.color == org.pruden.tablero.models.Color.White) {
                                                if(startPos == "e1" && endPos == "g1") {
                                                    val rook = Globals.chessBoard[7][7].pieceOnBox!!
                                                    Globals.chessBoard[7][7].pieceOnBox = null
                                                    Globals.chessBoard[7][5].pieceOnBox = rook
                                                    rook.position = Pair(5,7)
                                                }else if(startPos == "e1" && endPos == "c1") {
                                                    val rook = Globals.chessBoard[7][0].pieceOnBox!!
                                                    Globals.chessBoard[7][0].pieceOnBox = null
                                                    Globals.chessBoard[7][3].pieceOnBox = rook
                                                    rook.position = Pair(3,7)
                                                }
                                            }else{
                                                if(startPos == "e8" && endPos == "g8") {
                                                    val rook = Globals.chessBoard[0][7].pieceOnBox!!
                                                    Globals.chessBoard[0][7].pieceOnBox = null
                                                    Globals.chessBoard[0][5].pieceOnBox = rook
                                                    rook.position = Pair(5,0)
                                                }else if(startPos == "e8" && endPos == "c8") {
                                                    val rook = Globals.chessBoard[0][0].pieceOnBox!!
                                                    Globals.chessBoard[0][0].pieceOnBox = null
                                                    Globals.chessBoard[0][3].pieceOnBox = rook
                                                    rook.position = Pair(3,0)
                                                }
                                            }
                                        }
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
                                        if(
                                            (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.color == org.pruden.tablero.models.Color.White && Globals.isWhiteMove.value)
                                            || (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.color == org.pruden.tablero.models.Color.Black && !Globals.isWhiteMove.value)
                                            ) {
                                            Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.isSelected.value = true
                                            Globals.possibleMoves.value = MoveCalculator.getPosibleMoves(
                                                Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!
                                            )
                                        }
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
}




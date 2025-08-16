package org.pruden.tablero.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.LastMove
import org.pruden.tablero.utils.CastleHandler
import org.pruden.tablero.utils.CellHandler
import org.pruden.tablero.utils.History
import org.pruden.tablero.utils.MoveCalculator
import org.pruden.tablero.utils.PromotionHandler


@Composable
fun ChessBoard() {
    if(Globals.isBoardLoaded.value) {
        key(Globals.refreshBoard.value) {
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
                                    var promoteDone = false
                                    var promoteCanceled = false



                                    // Checks







                                    if (Globals.isWhitePromotion.value || Globals.isBlackPromotion.value) {
                                        promoteCanceled = PromotionHandler.handlePromotionClick(clickedRow, clickedCol)
                                        promoteDone = true
                                        Globals.refreshBoard.value = !Globals.refreshBoard.value
                                    }

                                    // Realizar movimiento
                                    if(!promoteDone) {
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


                                                History.push(selCol, selRow, clickedCol, clickedRow)
                                                Globals.lastPieceStartPos = Globals.chessBoard[selRow][selCol].pieceOnBox!!.position


                                                Globals.lastMove.value = LastMove(
                                                    from = Pair(selCol, selRow),
                                                    to = Pair(clickedCol, clickedRow)
                                                )


                                                Globals.chessBoard[clickedRow][clickedCol].pieceOnBox = Globals.chessBoard[selRow][selCol].pieceOnBox
                                                Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.position = Pair(clickedCol, clickedRow)
                                                Globals.chessBoard[selRow][selCol].pieceOnBox = null


                                                val movedPiece = Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!

                                                PromotionHandler.verifyPromotion(movedPiece)

                                                if (Globals.isWhitePromotion.value) {
                                                    PromotionHandler.preparePromotionSelectionWhite(movedPiece)
                                                } else if (Globals.isBlackPromotion.value) {
                                                    PromotionHandler.preparePromotionSelectionBlack(movedPiece)
                                                }else{
                                                    Globals.lastMove.value = LastMove(
                                                        from = Pair(selCol, selRow),
                                                        to = Pair(clickedCol, clickedRow)
                                                    )
                                                    Globals.isWhiteMove.value = !Globals.isWhiteMove.value
                                                }

                                                // Castle
                                                CastleHandler.disableCastleIfKingOrRookMoved(movedPiece = movedPiece)

                                                CastleHandler.moveRookOnCastle(
                                                    movedPiece = movedPiece,
                                                    selCol = selCol,
                                                    selRow = selRow,
                                                    clickedRow = clickedRow,
                                                    clickedCol = clickedCol,
                                                )
                                            }
                                        }
                                    }else{
                                        if(!promoteCanceled){
                                            Globals.isWhiteMove.value = !Globals.isWhiteMove.value
                                        }
                                    }

                                    if(!promoteDone || promoteCanceled) {
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

                                        // Comprobar si hay jaque
                                        if (Globals.isWhiteMove.value) {
                                            val attacked = MoveCalculator.calculateCellsControledByOponent(org.pruden.tablero.models.Color.White)
                                            val wKing = CellHandler.getKingByColor(org.pruden.tablero.models.Color.White)
                                            val inCheck = attacked.contains(wKing.position)
                                            Globals.whiteIsChecked.value = inCheck
                                            Globals.checkedKingPos.value = if (inCheck) wKing.position else null
                                        } else {
                                            val attacked = MoveCalculator.calculateCellsControledByOponent(org.pruden.tablero.models.Color.Black)
                                            val bKing = CellHandler.getKingByColor(org.pruden.tablero.models.Color.Black)
                                            val inCheck = attacked.contains(bKing.position)
                                            Globals.blackIsChecked.value = inCheck
                                            Globals.checkedKingPos.value = if (inCheck) bKing.position else null
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
}
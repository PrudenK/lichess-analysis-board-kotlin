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
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.castle.CastleHandler
import org.pruden.tablero.utils.chessBoard.ChessBoardActionHandler
import org.pruden.tablero.utils.moves.History
import org.pruden.tablero.utils.promotion.PromotionHandler
import kotlin.math.abs


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

                                    if (Globals.isWhitePromotion.value || Globals.isBlackPromotion.value) {
                                        promoteCanceled = PromotionHandler.handlePromotionClick(clickedRow, clickedCol)
                                        promoteDone = true
                                        Globals.refreshBoard.value = !Globals.refreshBoard.value
                                    }

                                    if(!promoteDone) {
                                        if (Globals.possibleMoves.value.contains(Pair(clickedCol, clickedRow))) {
                                            val selectedPiecePos = ChessBoardActionHandler.getSelectedPiecePos()

                                            if (selectedPiecePos != null) {
                                                moveWasDone = true
                                                val (selCol, selRow) = selectedPiecePos

                                                History.push(selCol, selRow, clickedCol, clickedRow)

                                                ChessBoardActionHandler.saveLastMove(clickedRow, clickedCol, selRow, selCol)

                                                val movedPiece = ChessBoardActionHandler.movePiece(clickedRow, clickedCol, selRow, selCol)

                                                if(Globals.posiblePassant){
                                                    if(Pair(clickedCol, clickedRow) == Globals.enPassantCell) {
                                                        val offset = if(Globals.isWhiteMove.value) -1 else 1

                                                        val (row, col) = Globals.enPassantCell
                                                        Globals.chessBoard[col - offset][row].pieceOnBox = null
                                                        Globals.refreshBoard.value = !Globals.refreshBoard.value
                                                    }
                                                }

                                                Globals.posiblePassant = false

                                                if(movedPiece.type == PieceType.Pawn){
                                                    if(abs(selRow - clickedRow) == 2){
                                                        Globals.posiblePassant = true
                                                        Globals.colPassant = selCol
                                                    }
                                                }

                                                ChessBoardActionHandler.makePromotionOrCompleteMove(movedPiece, clickedRow, clickedCol, selRow, selCol)

                                                CastleHandler.disableCastleIfKingOrRookMoved(movedPiece)
                                                CastleHandler.moveRookOnCastle(movedPiece, selRow, selCol, clickedRow,clickedCol)
                                            }
                                        }
                                    }else{
                                        if(!promoteCanceled){
                                            Globals.isWhiteMove.value = !Globals.isWhiteMove.value
                                        }
                                    }

                                    if(!promoteDone || promoteCanceled) {
                                        ChessBoardActionHandler.removePieceSelection()

                                        if(!moveWasDone) {
                                            ChessBoardActionHandler.tryToSelectAPiece(clickedRow, clickedCol)
                                        }

                                        ChessBoardActionHandler.verifyIfCheck()
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
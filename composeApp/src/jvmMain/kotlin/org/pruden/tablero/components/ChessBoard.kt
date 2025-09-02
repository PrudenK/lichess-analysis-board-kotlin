package org.pruden.tablero.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.castle.CastleHandler
import org.pruden.tablero.utils.chessBoard.ChessBoardActionHandler
import org.pruden.tablero.utils.moves.History
import org.pruden.tablero.utils.nodes.MovesNodesManager
import org.pruden.tablero.utils.notation.NotationHandler
import org.pruden.tablero.utils.promotion.PromotionHandler
import org.pruden.tablero.utils.result.ResultHandler


@Composable
fun ChessBoard(modifier: Modifier) {
    if (Globals.isBoardLoaded.value) {
        key(Globals.refreshBoard.value) {
            val density = LocalDensity.current
            val firstPadding = 10
            val border = 4
            val secondPadding = 4
            val total = firstPadding + border + secondPadding





            Box(
                modifier = modifier
                    .padding(
                        start = if (Globals.isBoardRotated.value) 0.dp else firstPadding.dp,
                        top = if (Globals.isBoardRotated.value) 0.dp else firstPadding.dp,
                        end = if (Globals.isBoardRotated.value) firstPadding.dp else 0.dp,
                        bottom = if (Globals.isBoardRotated.value) firstPadding.dp else 0.dp
                    )
                    .border(border.dp, Colors.secondary)
                    .padding(secondPadding.dp)
                    .onGloballyPositioned {
                        Globals.boardHeightDp.value = with(density) { it.size.height.toDp() }
                        Globals.boardWidthDp.value = with(density) { it.size.width.toDp() }
                        Globals.boardHeightDp.value += (total).dp
                        Globals.boardWidthDp.value += (border + secondPadding).dp
                        Globals.cellSizePx.value = with(density) { Globals.BOX_SIZE.dp.toPx() }
                    }
            ) {



                Column {
                    repeat(Globals.BOX_HEIGHT) { rowY ->
                        Row {
                            repeat(Globals.BOX_WIDTH) { columnX ->
                                Cell(
                                    columnX = columnX,
                                    rowY = rowY,
                                    cell = Globals.chessBoard[rowY][columnX],
                                    onClick = { clickedCol, clickedRow ->
                                        if (!Globals.isGameOver.value) {
                                            var moveWasDone = false
                                            var promoteDone = false
                                            var promoteCanceled = false
                                            if (Globals.isWhitePromotion.value || Globals.isBlackPromotion.value) {
                                                promoteCanceled = PromotionHandler.handlePromotionClick(clickedRow, clickedCol)
                                                promoteDone = true
                                                Globals.refreshBoard.value = !Globals.refreshBoard.value
                                                if (!promoteCanceled) NotationHandler.annotateCheckIfAny(true)
                                            }
                                            if (!promoteDone) {
                                                if (Globals.possibleMoves.value.contains(Pair(clickedCol, clickedRow))) {
                                                    val selectedPiecePos = ChessBoardActionHandler.getSelectedPiecePos()
                                                    if (selectedPiecePos != null) {
                                                        moveWasDone = true
                                                        val (selCol, selRow) = selectedPiecePos

                                                        History.push(selCol, selRow, clickedCol, clickedRow)
                                                        ChessBoardActionHandler.saveLastMove(clickedRow, clickedCol, selRow, selCol)

                                                        val movedPiece = ChessBoardActionHandler.movePiece(clickedRow, clickedCol, selRow, selCol)

                                                        ChessBoardActionHandler.enPassantCalculations(movedPiece, clickedRow, clickedCol, selRow, selCol)
                                                        ChessBoardActionHandler.makePromotionOrCompleteMove(movedPiece, clickedRow, clickedCol, selRow, selCol)

                                                        CastleHandler.disableCastleIfKingOrRookMoved(movedPiece, selRow, selCol)
                                                        CastleHandler.moveRookOnCastle(movedPiece, selRow, selCol, clickedRow, clickedCol)

                                                        NotationHandler.annotateCheckIfAny()
                                                    }
                                                }
                                            } else {
                                                if (!promoteCanceled) {
                                                    Globals.isWhitePromotion.value = false
                                                    Globals.isBlackPromotion.value = false
                                                    Globals.isWhiteMove.value = !Globals.isWhiteMove.value
                                                    ChessBoardActionHandler.verifyIfCheck()
                                                    MovesNodesManager.updateLastMoveFen()
                                                }
                                            }
                                            if (!promoteDone || promoteCanceled) {
                                                ChessBoardActionHandler.removePieceSelection()
                                                if (!moveWasDone) {
                                                    ChessBoardActionHandler.tryToSelectAPiece(clickedRow, clickedCol)
                                                } else if (!Globals.isWhitePromotion.value && !Globals.isBlackPromotion.value) {
                                                    MovesNodesManager.updateLastMoveFen()
                                                }
                                                ChessBoardActionHandler.verifyIfCheck()
                                            }
                                            Globals.refreshMovesPanel.value = !Globals.refreshMovesPanel.value
                                            ResultHandler.calculateResultAfterMove()
                                        }
                                    },
                                )
                            }
                        }
                    }
                }

                if (Globals.isDragging.value && Globals.dragPng.value != null) {
                    val cs = Globals.cellSizePx.value
                    Image(
                        painter = painterResource(Globals.dragPng.value!!),
                        contentDescription = null,
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    (Globals.dragPointerPx.value.x - cs / 2).toInt(),
                                    (Globals.dragPointerPx.value.y - cs / 2).toInt()
                                )
                            }
                            .size(Globals.BOX_SIZE.dp)
                            .rotate(if (Globals.isBoardRotated.value) 180f else 0f)
                    )
                }

                if(Globals.isModuleActivated.value){
                    MoveArrowOverlay(
                        from = Globals.bestMove.value?.fromToPair(),
                        to = Globals.bestMove.value?.toToPair(),
                        cellSize = with(LocalDensity.current) { Globals.BOX_SIZE.dp.toPx() },
                        modifier = Modifier.size((Globals.BOX_SIZE * 8).dp)
                    )
                }
            }
        }
    }
}

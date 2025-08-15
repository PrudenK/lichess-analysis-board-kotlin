package org.pruden.tablero.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.NonCancellable.key
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.CastleHandler
import org.pruden.tablero.utils.CellHandler
import org.pruden.tablero.utils.MoveCalculator
import org.pruden.tablero.utils.PieceProvider
import org.pruden.tablero.utils.PromotionHandler
import org.pruden.tablero.utils.loadChessBoard
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.bB
import tableroajedrez.composeapp.generated.resources.bN
import tableroajedrez.composeapp.generated.resources.bQ
import tableroajedrez.composeapp.generated.resources.bR
import tableroajedrez.composeapp.generated.resources.wB
import tableroajedrez.composeapp.generated.resources.wN
import tableroajedrez.composeapp.generated.resources.wQ
import tableroajedrez.composeapp.generated.resources.wR
import java.lang.Thread.sleep


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

                                    if(Globals.isWhitePromotion.value) {
                                        println("$clickedCol, $clickedRow")
                                        val promotionCol = Globals.promotionCol
                                        val pawnPromoted = Globals.pawnPromoted!!


                                        val piecePromoted = when(clickedRow){
                                            1 -> PieceProvider.getWhiteKnight(pawnPromoted)
                                            2 -> PieceProvider.getWhiteRook(pawnPromoted)
                                            3 -> PieceProvider.getWhiteBishop(pawnPromoted)
                                            else -> PieceProvider.getWhiteQueen(pawnPromoted)
                                        }

                                        Globals.chessBoard[0][promotionCol].pieceOnBox = piecePromoted
                                        Globals.chessBoard[1][promotionCol].pieceOnBox = Globals.promotionBuffer[0]
                                        Globals.chessBoard[2][promotionCol].pieceOnBox = Globals.promotionBuffer[1]
                                        Globals.chessBoard[3][promotionCol].pieceOnBox = Globals.promotionBuffer[2]
                                        Globals.promotionBuffer.clear()

                                        CellHandler.setCellsDisable(false)

                                        Globals.isWhitePromotion.value = false
                                        promoteDone = true

                                        Globals.refreshBoard.value = !Globals.refreshBoard.value


                                    }else if(Globals.isBlackPromotion.value) {
                                        val promotionCol = Globals.promotionCol
                                        val pawnPromoted = Globals.pawnPromoted!!


                                        val piecePromoted = when(clickedRow){
                                            6 -> PieceProvider.getBlackKnight(pawnPromoted)
                                            5 -> PieceProvider.getBlackRook(pawnPromoted)
                                            4 -> PieceProvider.getBlackBishop(pawnPromoted)
                                            else -> PieceProvider.getBlackQueen(pawnPromoted)
                                        }

                                        Globals.chessBoard[7][promotionCol].pieceOnBox = piecePromoted
                                        Globals.chessBoard[6][promotionCol].pieceOnBox = Globals.promotionBuffer[0]
                                        Globals.chessBoard[5][promotionCol].pieceOnBox = Globals.promotionBuffer[1]
                                        Globals.chessBoard[4][promotionCol].pieceOnBox = Globals.promotionBuffer[2]
                                        Globals.promotionBuffer.clear()

                                        CellHandler.setCellsDisable(false)

                                        Globals.isBlackPromotion.value = false
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

                                                Globals.chessBoard[clickedRow][clickedCol].pieceOnBox = Globals.chessBoard[selRow][selCol].pieceOnBox
                                                Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.position = Pair(clickedCol, clickedRow)
                                                Globals.chessBoard[selRow][selCol].pieceOnBox = null


                                                val movedPiece = Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!

                                                PromotionHandler.verifyPromotion(movedPiece)

                                                val promotionCol = Globals.promotionCol

                                                if(Globals.isWhitePromotion.value) {
                                                    CellHandler.setCellsDisable(true)

                                                    val c1 = Globals.chessBoard[0][promotionCol]
                                                    val c2 = Globals.chessBoard[1][promotionCol]
                                                    val c3 = Globals.chessBoard[2][promotionCol]
                                                    val c4 = Globals.chessBoard[3][promotionCol]

                                                    c1.disable = false
                                                    c2.disable = false
                                                    c3.disable = false
                                                    c4.disable = false

                                                    Globals.promotionBuffer.add(c2.pieceOnBox)
                                                    Globals.promotionBuffer.add(c3.pieceOnBox)
                                                    Globals.promotionBuffer.add(c4.pieceOnBox)

                                                    c1.pieceOnBox = PieceProvider.getWhiteQueen(movedPiece)
                                                    c2.pieceOnBox = PieceProvider.getWhiteKnight(movedPiece)
                                                    c3.pieceOnBox = PieceProvider.getWhiteRook(movedPiece)
                                                    c4.pieceOnBox = PieceProvider.getWhiteBishop(movedPiece)

                                                    Globals.pawnPromoted = movedPiece
                                                }else if(Globals.isBlackPromotion.value) {
                                                    CellHandler.setCellsDisable(true)

                                                    val c1 = Globals.chessBoard[7][promotionCol]
                                                    val c2 = Globals.chessBoard[6][promotionCol]
                                                    val c3 = Globals.chessBoard[5][promotionCol]
                                                    val c4 = Globals.chessBoard[4][promotionCol]

                                                    c1.disable = false
                                                    c2.disable = false
                                                    c3.disable = false
                                                    c4.disable = false

                                                    Globals.promotionBuffer.add(c2.pieceOnBox)
                                                    Globals.promotionBuffer.add(c3.pieceOnBox)
                                                    Globals.promotionBuffer.add(c4.pieceOnBox)

                                                    c1.pieceOnBox = PieceProvider.getBlackQueen(movedPiece)
                                                    c2.pieceOnBox = PieceProvider.getBlackKnight(movedPiece)
                                                    c3.pieceOnBox = PieceProvider.getBlackRook(movedPiece)
                                                    c4.pieceOnBox = PieceProvider.getBlackBishop(movedPiece)

                                                    Globals.pawnPromoted = movedPiece
                                                }else{
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
                                        Globals.isWhiteMove.value = !Globals.isWhiteMove.value
                                    }




                                    if(!promoteDone) {
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
        if (Globals.isWhitePromotion.value || Globals.isBlackPromotion.value) {
            //PromotionMenuAtSquare()
        }

    }
}



@Composable
fun PromotionMenuAtSquare() {
    val col = Globals.promotionCol
    val row = if (Globals.isWhitePromotion.value) 0 else 7
    val pieces = if (Globals.isWhitePromotion.value) {
        listOf(
            PieceType.Queen to Res.drawable.wQ,
            PieceType.Knight to Res.drawable.wN,
            PieceType.Rook to Res.drawable.wR,
            PieceType.Bishop to Res.drawable.wB
        )
    } else {
        listOf(
            PieceType.Queen to Res.drawable.bQ,
            PieceType.Knight to Res.drawable.bN,
            PieceType.Rook to Res.drawable.bR,
            PieceType.Bishop to Res.drawable.bB
        )
    }

    Box(
        modifier = Modifier
            .offset(
                x = (col * Globals.BOX_SIZE).dp,
                y = (if (Globals.isWhitePromotion.value) 0 else (row - 3) * Globals.BOX_SIZE).dp
            )
    ) {
        Column {
            pieces.forEach { (type, icon) ->
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(Globals.BOX_SIZE.dp)
                        .clickable {
                            val piece = Piece(
                                id = 1,
                                type = type,
                                color = if (Globals.isWhitePromotion.value) org.pruden.tablero.models.Color.White else org.pruden.tablero.models.Color.Black,
                                png = icon,
                                position = Pair(col, row)
                            )
                            Globals.chessBoard[row][col].pieceOnBox = piece
                            Globals.isWhitePromotion.value = false
                            Globals.isBlackPromotion.value = false
                        }
                )
            }
        }
    }
}

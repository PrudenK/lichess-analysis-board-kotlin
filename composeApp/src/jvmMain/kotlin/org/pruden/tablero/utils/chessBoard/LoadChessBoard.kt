package org.pruden.tablero.utils.chessBoard

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.*
import org.pruden.tablero.utils.notation.FenConverter
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.bB
import tableroajedrez.composeapp.generated.resources.bK
import tableroajedrez.composeapp.generated.resources.bN
import tableroajedrez.composeapp.generated.resources.bP
import tableroajedrez.composeapp.generated.resources.bQ
import tableroajedrez.composeapp.generated.resources.bR
import tableroajedrez.composeapp.generated.resources.wB
import tableroajedrez.composeapp.generated.resources.wK
import tableroajedrez.composeapp.generated.resources.wN
import tableroajedrez.composeapp.generated.resources.wP
import tableroajedrez.composeapp.generated.resources.wQ
import tableroajedrez.composeapp.generated.resources.wR

fun loadChessBoard() {
    repeat(Globals.BOX_HEIGHT) { columnX ->
        repeat(Globals.BOX_WIDTH) { rowY ->
            val notation = "${'a' + columnX}${8 - rowY}"

            Globals.chessBoard[rowY][columnX] = BoxModel(
                boxNotation = notation,
                color = if ((columnX + rowY) % 2 == 0) Globals.WhiteBox else Globals.BlackBox,
                pieceOnBox = if(rowY >= 2 && rowY <= 5 ) null else {
                    val isBlackPiece = rowY < 2

                    val color = when{
                        isBlackPiece -> Color.Black
                        else -> Color.White
                    }

                    val typePiece = when{
                        rowY == 1 || rowY == 6 -> PieceType.Pawn
                        columnX == 0 || columnX == 7 -> PieceType.Rook
                        columnX == 1 || columnX == 6 -> PieceType.Knight
                        columnX == 2 || columnX == 5 -> PieceType.Bishop
                        columnX == 3 -> PieceType.Queen
                        else -> PieceType.King
                    }

                    val png = when (typePiece) {
                        PieceType.Pawn   -> if (isBlackPiece) Res.drawable.bP else Res.drawable.wP
                        PieceType.Rook   -> if (isBlackPiece) Res.drawable.bR else Res.drawable.wR
                        PieceType.Knight -> if (isBlackPiece) Res.drawable.bN else Res.drawable.wN
                        PieceType.Bishop -> if (isBlackPiece) Res.drawable.bB else Res.drawable.wB
                        PieceType.Queen  -> if (isBlackPiece) Res.drawable.bQ else Res.drawable.wQ
                        else   -> if (isBlackPiece) Res.drawable.bK else Res.drawable.wK
                    }

                    Piece(
                        type = typePiece,
                        color = color,
                        png = png,
                        position = Pair(columnX, rowY)
                    )
                }
            )
        }
    }




    Globals.movesNodesBuffer.value.add(
        MoveNode(
            id = "root",
            fen = FenConverter.chessBoardToFen(Globals.chessBoard),
            isActualMove = true
        )
    )
}
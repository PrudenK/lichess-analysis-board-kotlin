package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
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
    repeat(Globals.BOX_HEIGHT) { y ->
        repeat(Globals.BOX_WIDTH) { x ->
            val notation = "${'a' + x}${8 - y}"

            Globals.chessBoard[x][y] = BoxModel(
                boxNotation = notation,
                color = if ((x + y) % 2 == 0) Globals.WhiteBox else Globals.BlackBox,
                pieceOnBox = if(y >= 2 && y <= 5 ) null else {
                    val isBlackPiece = y < 2

                    val color = when{
                        isBlackPiece -> org.pruden.tablero.models.Color.Black
                        else -> org.pruden.tablero.models.Color.White
                    }

                    val typePiece = when{
                        y == 1 || y == 6 -> PieceType.Pawn
                        x == 0 || x == 7 -> PieceType.Rook
                        x == 1 || x == 6 -> PieceType.Knight
                        x == 2 || x == 5 -> PieceType.Bishop
                        x == 3 -> PieceType.Queen
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
                        id = if(isBlackPiece) x + y*8 else (x + y*8) - 32,
                        type = typePiece,
                        color = color,
                        png = png
                    )
                }
            )
        }
    }
}
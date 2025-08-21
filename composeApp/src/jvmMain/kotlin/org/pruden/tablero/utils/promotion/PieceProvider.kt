package org.pruden.tablero.utils.promotion

import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import tableroajedrez.composeapp.generated.resources.*

object PieceProvider {
    fun getWhiteKing(position: Pair<Int, Int>) = Piece(
        type = PieceType.King,
        color = Color.White,
        position = position,
        png = Res.drawable.wK
    )

    fun getWhitePawn(position: Pair<Int, Int>) = Piece(
        type = PieceType.Pawn,
        color = Color.White,
        position = position,
        png = Res.drawable.wP
    )

    fun getWhiteQueen(position: Pair<Int, Int>) = Piece(
        type = PieceType.Queen,
        color = Color.White,
        position = position,
        png = Res.drawable.wQ
    )

    fun getWhiteRook(position: Pair<Int, Int>) = Piece(
        type = PieceType.Rook,
        color = Color.White,
        position = position,
        png = Res.drawable.wR
    )

    fun getWhiteBishop(position: Pair<Int, Int>) = Piece(
        type = PieceType.Bishop,
        color = Color.White,
        position = position,
        png = Res.drawable.wB
    )

    fun getWhiteKnight(position: Pair<Int, Int>) = Piece(
        type = PieceType.Knight,
        color = Color.White,
        position = position,
        png = Res.drawable.wN
    )

    fun getBlackKing(position: Pair<Int, Int>) = Piece(
        type = PieceType.King,
        color = Color.Black,
        position = position,
        png = Res.drawable.bK
    )

    fun getBlackQueen(position: Pair<Int, Int>) = Piece(
        type = PieceType.Queen,
        color = Color.Black,
        position = position,
        png = Res.drawable.bQ
    )

    fun getBlackRook(position: Pair<Int, Int>) = Piece(
        type = PieceType.Rook,
        color = Color.Black,
        position = position,
        png = Res.drawable.bR
    )

    fun getBlackBishop(position: Pair<Int, Int>) = Piece(
        type = PieceType.Bishop,
        color = Color.Black,
        position = position,
        png = Res.drawable.bB
    )

    fun getBlackKnight(position: Pair<Int, Int>) = Piece(
        type = PieceType.Knight,
        color = Color.Black,
        position = position,
        png = Res.drawable.bN
    )

    fun getBlackPawn(position: Pair<Int, Int>) = Piece(
        type = PieceType.Pawn,
        color = Color.Black,
        position = position,
        png = Res.drawable.bP
    )
}

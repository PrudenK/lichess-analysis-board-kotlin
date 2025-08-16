package org.pruden.tablero.utils.promotion

import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.bB
import tableroajedrez.composeapp.generated.resources.bN
import tableroajedrez.composeapp.generated.resources.bQ
import tableroajedrez.composeapp.generated.resources.bR
import tableroajedrez.composeapp.generated.resources.wB
import tableroajedrez.composeapp.generated.resources.wN
import tableroajedrez.composeapp.generated.resources.wQ
import tableroajedrez.composeapp.generated.resources.wR

object PieceProvider {
    fun getWhiteQueen(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Queen,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.wQ
    )

    fun getWhiteRook(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Rook,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.wR
    )

    fun getWhiteBishop(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Bishop,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.wB
    )

    fun getWhiteKnight(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Knight,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.wN
    )

    fun getBlackQueen(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Queen,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.bQ
    )

    fun getBlackRook(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Rook,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.bR
    )

    fun getBlackBishop(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Bishop,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.bB
    )

    fun getBlackKnight(pawn: Piece) = Piece(
        id = pawn.id,
        type = PieceType.Knight,
        color = pawn.color,
        position = pawn.position,
        png = Res.drawable.bN
    )
}

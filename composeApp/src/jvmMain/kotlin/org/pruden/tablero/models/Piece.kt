package org.pruden.tablero.models

data class Piece(
    val id: Int,
    val type: PieceType,
    val color: Color,
    val png: String,
)

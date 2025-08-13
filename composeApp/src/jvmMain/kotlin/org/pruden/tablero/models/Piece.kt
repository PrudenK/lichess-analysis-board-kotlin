package org.pruden.tablero.models

import org.jetbrains.compose.resources.DrawableResource

data class Piece(
    val id: Int,
    val type: PieceType,
    val color: Color,
    val png: DrawableResource? = null,
)

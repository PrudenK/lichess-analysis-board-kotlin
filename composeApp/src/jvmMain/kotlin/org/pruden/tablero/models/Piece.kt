package org.pruden.tablero.models

import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.DrawableResource

data class Piece(
    val id: Int,
    val type: PieceType,
    val color: Color,
    val png: DrawableResource? = null,
    val position: Pair<Int, Int>,
    var isSelected: MutableSet<Boolean> = mutableSetOf(false)
)

package org.pruden.tablero.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.jetbrains.compose.resources.DrawableResource

data class Piece(
    val id: Int,
    val type: PieceType,
    val color: Color,
    val png: DrawableResource? = null,
    val position: Pair<Int, Int>,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)

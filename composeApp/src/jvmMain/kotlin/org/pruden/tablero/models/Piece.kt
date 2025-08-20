package org.pruden.tablero.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.jetbrains.compose.resources.DrawableResource

data class Piece(
    val type: PieceType,
    val color: Color,
    val png: DrawableResource? = null,
    var position: Pair<Int, Int>,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
){
    fun positionToChessNotation(): String {
        val l = "abcdefgh"
        return l[position.first].toString() + (7 - position.second +1)
    }
}

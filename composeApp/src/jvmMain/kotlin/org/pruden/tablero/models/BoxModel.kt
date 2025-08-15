package org.pruden.tablero.models

data class BoxModel(
    val boxNotation: String = "",
    val color: androidx.compose.ui.graphics.Color? = null,
    var pieceOnBox: Piece? = null
){
    fun isFreeCell() = pieceOnBox == null
}

package org.pruden.tablero.models

data class MoveRecord(
    val fromCol: Int,
    val fromRow: Int,
    val toCol: Int,
    val toRow: Int,
    val movedBefore: Piece?,
    val capturedBefore: Piece?,
    val whiteCastle: Triple<Boolean, Boolean, Boolean>,
    val blackCastle: Triple<Boolean, Boolean, Boolean>,
    val isWhiteMove: Boolean
)
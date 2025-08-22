package org.pruden.tablero.models

data class NotationMove(
    var san: String,
    var fen: String,
    var isActualMove: Boolean,
    val from: Pair<Int, Int>,
    val to: Pair<Int, Int>
){
    fun getColor() = if(fen.split(" ")[1] == "w") Color.White else Color.Black
    fun isWhiteMove() = getColor() == Color.White
    fun toLastMove() = LastMove(from, to)
}

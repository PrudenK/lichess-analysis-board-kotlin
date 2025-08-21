package org.pruden.tablero.models

data class NotationMove(
    var san: String,
    var fen: String,
    var isActualMove: Boolean
){
    fun getColor() = if(fen.split(" ")[1] == "w") Color.White else Color.Black
    fun isWhiteMove() = getColor() == Color.White

}

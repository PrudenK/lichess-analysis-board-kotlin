package org.pruden.tablero.utils

object NotationHandler {
    fun positionToChessNotation(position: Pair<Int, Int>): String {
        val l = "abcdefgh"
        return l[position.first].toString() + (7 - position.second +1)
    }
}
package org.pruden.tablero.models

data class BestMove(
    var from: String?,
    var to: String?,
    var san: String?
){
    fun fromToPair() = toPairNotation(from)
    fun toToPair() = toPairNotation(to)

    private fun toPairNotation(pos: String?): Pair<Int, Int>? {
        if(pos == null || pos.length != 2) return null
        val file = pos[0]
        val rank = pos[1]
        val col = file - 'a'
        val row = 8 - (rank - '0')
        if(col !in 0..7 || row !in 0..7) return null
        return Pair(col, row)
    }
}

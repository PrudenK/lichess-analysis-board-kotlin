package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.NotationMove

object NotationMovesHandler {


    fun addNotationMove(san: String, from: Pair<Int, Int>, to: Pair<Int, Int>) {
        val list = Globals.movesBufferNotation.value.toMutableList()

        list.forEach { it.isActualMove = false }

        val fen = FenConverter.chessBoardToFen(Globals.chessBoard)

        list.add(NotationMove(san, fen, true, from, to))

        Globals.movesBufferNotation.value = list
    }


    fun updateLastMoveFen(){
        val move = Globals.movesBufferNotation.value.last()
        move.fen = FenConverter.chessBoardToFen(Globals.chessBoard)
    }


    fun modifyLastMoveNotation(adder : String){
        if (Globals.movesBufferNotation.value.isEmpty()) return

        val lastMove = Globals.movesBufferNotation.value.last()

        if(adder == "#"){
            lastMove.san = lastMove.san.replace("+", "")
        }

        lastMove.san += adder
        Globals.movesBufferNotation.value[Globals.movesBufferNotation.value.size - 1] = lastMove
    }
}
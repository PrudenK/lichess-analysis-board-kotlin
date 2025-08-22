package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.NotationMove

object NotationMovesHandler {


    fun addNotationMove(san: String, from: Pair<Int, Int>, to: Pair<Int, Int>) {
        for (m in Globals.movesBufferNotation.value){
            m.isActualMove = false
        }

        val fen = FenConverter.chessBoardToFen(Globals.chessBoard)

        val move = NotationMove(
            san = san,
            fen = fen,
            isActualMove = true,
            from = from,
            to = to
        )

        Globals.movesBufferNotation.value.add(move)
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
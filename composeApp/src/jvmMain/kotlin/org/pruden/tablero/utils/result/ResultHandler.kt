package org.pruden.tablero.utils.result

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.utils.moves.MoveCalculator


object ResultHandler {
    fun getResultToString(): String {
        return when (Globals.result.value) {
            0 -> "White wins"
            1 -> "Black wins"
            2 -> "Draw by stalemate"
            else -> ""
        }
    }

    fun calculateResultAfterMove(){
        if(Globals.whiteIsChecked.value){
            val moves = MoveCalculator.getAllPossibleMovesByColor(Color.White)

            if (moves.isEmpty()) {
                Globals.result.value = 1
            }
        }else if(Globals.blackIsChecked.value){
            val moves = MoveCalculator.getAllPossibleMovesByColor(Color.Black)

            if (moves.isEmpty()) {
                Globals.result.value = 0
            }
        }else{
            if(Globals.isWhiteMove.value){
                val moves = MoveCalculator.getAllPossibleMovesByColor(Color.White)

                if (moves.isEmpty()) {
                    Globals.result.value = 2
                }
            }else{
                val moves = MoveCalculator.getAllPossibleMovesByColor(Color.Black)

                if (moves.isEmpty()) {
                    Globals.result.value = 2
                }
            }
        }
    }
}
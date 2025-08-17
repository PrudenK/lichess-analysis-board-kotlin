package org.pruden.tablero.utils.result

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.moves.MoveCalculator


object ResultHandler {
    fun getResultToString(): String {
        return when (Globals.result.value) {
            0 -> "White wins"
            1 -> "Black wins"
            2 -> "Draw by stalemate"
            3 -> "Draw by death position"
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

        calculateDeadPosition()
    }

    fun calculateDeadPosition() {
        var knights = 0
        var bishopsOnWhite = 0
        var bishopsOnBlack = 0

        for (i in 0..7) {
            for (j in 0..7) {
                val piece = Globals.chessBoard[i][j].pieceOnBox ?: continue
                when (piece.type) {
                    PieceType.Pawn, PieceType.Rook, PieceType.Queen -> return
                    PieceType.Knight -> {
                        knights++
                        if (knights >= 2) return
                    }
                    PieceType.Bishop -> {
                        if (Globals.chessBoard[i][j].color == Globals.WhiteBox) bishopsOnWhite++
                        else bishopsOnBlack++
                    }
                    else -> {}
                }
            }
        }

        val total = knights + bishopsOnWhite + bishopsOnBlack

        if (total == 0) {
            Globals.result.value = 3
        } else if (total == 1 && knights == 1) {
            Globals.result.value = 3 // rey+caballo
        } else if (total == 1 && (bishopsOnWhite == 1 || bishopsOnBlack == 1)) {
            Globals.result.value = 3 // rey+alfil
        } else if (knights == 0 && (bishopsOnWhite == 0 || bishopsOnBlack == 0)) {
            Globals.result.value = 3 // solo alfiles en un mismo color
        }
    }

}
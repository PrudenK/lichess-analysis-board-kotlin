package org.pruden.tablero.utils.result

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.moves.MoveCalculator
import org.pruden.tablero.utils.notation.NotationHandler


object ResultHandler {
    fun getResultToString(): String {
        return when (Globals.result.value) {
            0 -> "White wins"
            1 -> "Black wins"
            2 -> "Draw by stalemate"
            3 -> "Draw by death position"
            4 -> "Draw by fifty-move rule"
            5 -> "Draw by seventy-five-move rule"

            else -> ""
        }
    }

    fun calculateResultAfterMove(){
        if(Globals.whiteIsChecked.value){
            val moves = MoveCalculator.getAllPossibleMovesByColor(Color.White)

            if (moves.isEmpty()) {
                manageResult(1)
            }
        }else if(Globals.blackIsChecked.value){
            val moves = MoveCalculator.getAllPossibleMovesByColor(Color.Black)

            if (moves.isEmpty()) {
                manageResult(0)
            }
        }else{
            if(Globals.isWhiteMove.value){
                val moves = MoveCalculator.getAllPossibleMovesByColor(Color.White)

                if (moves.isEmpty()) {
                    manageResult(2)
                }
            }else{
                val moves = MoveCalculator.getAllPossibleMovesByColor(Color.Black)

                if (moves.isEmpty()) {
                    manageResult(2)
                }
            }
        }

        if(isFiftyReclaimable()){
            // TODO activar boton para reclamar
            // manageResult(4)
        }

        if(isSeventyFiveAuto()){
            manageResult(5)
        }


        calculateDeadPosition()
    }

    fun isFiftyReclaimable() = Globals.halfMoves >= 100
    fun isSeventyFiveAuto() = Globals.halfMoves >= 150

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
            manageResult(3)
        } else if (total == 1 && knights == 1) {
            manageResult(3) // rey+caballo
        } else if (total == 1 && (bishopsOnWhite == 1 || bishopsOnBlack == 1)) {
            manageResult(3) // rey+alfil
        } else if (knights == 0 && (bishopsOnWhite == 0 || bishopsOnBlack == 0)) {
            manageResult(3) // solo alfiles en un mismo color
        }
    }

    private fun manageResult(i: Int){
        if(i == 0 || i == 1){
            NotationHandler.appendMate()
        }
        Globals.result.value = i
        Globals.isGameOver.value = true
    }
}
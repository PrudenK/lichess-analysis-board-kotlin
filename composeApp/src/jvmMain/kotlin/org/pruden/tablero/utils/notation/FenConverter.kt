package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.*

object FenConverter {

    fun chessBoardToFen(chessBoard: Array<Array<BoxModel>> = Globals.chessBoard, playNumber: Int): String{
        var result = ""
        for (row in chessBoard) {
            var empty = 0
            for (cell in row) {
                if(cell.pieceOnBox != null){
                    if(empty != 0) {
                        result += empty
                        empty = 0
                    }
                    result += pieceToLetter(cell.pieceOnBox!!)
                }else{
                    empty++
                }
            }
            if(empty != 0){
                result += empty
            }
            result += "/"
        }

        result = result.dropLast(1)

        result += " ${if(Globals.isWhiteMove.value) "w" else "b"} "

        result += castle()

        result += Globals.fenEnPassant

        result += " ${Globals.halfMoves}"

        result +=  " $playNumber"

        return result
    }


    private fun pieceToLetter(piece: Piece): String{
        val letter = when (piece.type) {
            PieceType.Pawn -> "P"
            PieceType.Rook -> "R"
            PieceType.Knight -> "N"
            PieceType.Bishop -> "B"
            PieceType.Queen -> "Q"
            PieceType.King -> "K"
            else -> ""
        }

        return if(piece.color == Color.White) letter else letter.lowercase()
    }

    private fun castle(): String{
        val wK = !Globals.whiteCastle.second && !Globals.whiteCastle.third
        val wQ = !Globals.whiteCastle.second && !Globals.whiteCastle.first
        val bK = !Globals.blackCastle.second && !Globals.blackCastle.third
        val bQ = !Globals.blackCastle.second && !Globals.blackCastle.first

        var castling = buildString {
            if (wK) append('K')
            if (wQ) append('Q')
            if (bK) append('k')
            if (bQ) append('q')
        }
        if (castling.isEmpty()) castling = "-"

        return "$castling "
    }
}
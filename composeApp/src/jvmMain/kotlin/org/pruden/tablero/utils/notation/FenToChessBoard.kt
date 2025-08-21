package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.globals.Globals.BOX_HEIGHT
import org.pruden.tablero.utils.promotion.PieceProvider

object FenToChessBoard {

    fun setBoardFromFen(fen: String = "rnbqk2r/1ppp1ppp/p4n2/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQK2R b kq - 1 5") {
        val spaceParts = fen.split(" ")
        val boardRows = spaceParts[0].split("/").toMutableList()

        for ((i, row) in boardRows.withIndex()){
            var rowResult = ""
            for(c in row){
                if(c.isDigit()){
                    val numC = c.toString().toInt()
                    repeat(numC){
                        rowResult += "0"
                    }
                }else{
                    rowResult += c
                }
            }
            boardRows[i] = rowResult

        }

        for((i, row) in Globals.chessBoard.withIndex()){
            for ((j, cell) in row.withIndex()){
                if(boardRows[i][j] == '0'){
                    cell.pieceOnBox = null
                }else{
                    cell.pieceOnBox = when(boardRows[i][j]){
                        'p' -> PieceProvider.getBlackPawn(Pair(j, i))
                        'r' -> PieceProvider.getBlackRook(Pair(j, i))
                        'n' -> PieceProvider.getBlackKnight(Pair(j, i))
                        'b' -> PieceProvider.getBlackBishop(Pair(j, i))
                        'q' -> PieceProvider.getBlackQueen(Pair(j, i))
                        'k' -> PieceProvider.getBlackKing(Pair(j, i))
                        'P' -> PieceProvider.getWhitePawn(Pair(j, i))
                        'R' -> PieceProvider.getWhiteRook(Pair(j, i))
                        'N' -> PieceProvider.getWhiteKnight(Pair(j, i))
                        'B' -> PieceProvider.getWhiteBishop(Pair(j, i))
                        'Q' -> PieceProvider.getWhiteQueen(Pair(j, i))
                        'K' -> PieceProvider.getWhiteKing(Pair(j, i))
                        else -> null
                    }
                }
            }
        }

        Globals.isWhiteMove.value = spaceParts[1] == "w"

        Globals.whiteCastle = Triple(
            !spaceParts[2].contains("Q"),
            false,
            !spaceParts[2].contains("K")
        )

        Globals.blackCastle = Triple(
            !spaceParts[2].contains("q"),
            false,
            !spaceParts[2].contains("k")
        )

        if(spaceParts[3] != "-"){
            Globals.posiblePassant = true
            Globals.colPassant = spaceParts[3][0].code - 'a'.code
            Globals.enPassantCell = Pair(
                spaceParts[3][0].code - 'a'.code,
                BOX_HEIGHT - (spaceParts[3][1].code - '1'.code + 1)
            )

        }

        Globals.fenEnPassant = spaceParts[3]
        Globals.halfMoves = spaceParts[4].toInt()

        Globals.refreshBoard.value = !Globals.refreshBoard.value
    }

}
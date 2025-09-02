package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.globals.Globals.BOX_HEIGHT
import org.pruden.tablero.utils.chessBoard.ChessBoardActionHandler
import org.pruden.tablero.utils.promotion.PieceProvider

object FenToChessBoard {
    fun setBoardFromFen(fen: String = "") {
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

        Globals.isWhiteMove.value = if(fen == Globals.initialFenPos) true else spaceParts[1] == "w"

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

        ChessBoardActionHandler.verifyIfCheck()

        Globals.refreshBoard.value = !Globals.refreshBoard.value
    }

}
package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object NotationHandler {


    fun addMoveToBuffer(
        piece: Piece,
        clickedCell: BoxModel
    ) {
        var wasCaptured = false
        var pieceLetter = ""

        if(clickedCell.pieceOnBox != null){
            wasCaptured = true
        }

        pieceLetter = letterOfAPiece(piece)

        val notation = when {
            wasCaptured -> "${pieceLetter}x${clickedCell.boxNotation}"


            else -> pieceLetter + piece.positionToChessNotation()
        }


        Globals.movesBuffer.value.add("$notation")
    }

    fun letterOfAPiece(piece: Piece): String {
        return when(piece.type){
            PieceType.King -> "K"
            PieceType.Bishop -> "B"
            PieceType.Queen -> "Q"
            PieceType.Rook -> "R"
            PieceType.Knight -> "N"
            else -> ""
        }
    }
}
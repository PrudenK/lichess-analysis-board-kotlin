package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object CellHandler {
    fun setCellsDisable(b: Boolean) {
        for (row in Globals.chessBoard) {
            for (cell in row) {
                cell.disable = b
            }
        }
    }

    fun getKingByColor(color: Color): Piece {
        for (row in Globals.chessBoard) {
            for (cell in row) {
                val piece = cell.pieceOnBox
                if (piece != null && piece.color == color && piece.type == PieceType.King) {
                    return piece
                }
            }
        }
        error("No king found — should never happen")
    }
}

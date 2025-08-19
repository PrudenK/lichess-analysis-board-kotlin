package org.pruden.tablero.utils.chessBoard

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
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


    fun copyBoard(original: Array<Array<BoxModel>>): Array<Array<BoxModel>> {
        return Array(original.size) { row ->
            Array(original[row].size) { col ->
                original[row][col].copy()
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

    fun findPiecesInBoard(piece: Piece, board: Array<Array<BoxModel>> = Globals.chessBoard): List<Piece> {
        val result = mutableListOf<Piece>()

        for (row in board) {
            for (cell in row) {
                val p = cell.pieceOnBox
                if (p != null && p.color == piece.color && p.type == piece.type) {
                    result.add(p)
                }
            }
        }

        return result - piece
    }
}

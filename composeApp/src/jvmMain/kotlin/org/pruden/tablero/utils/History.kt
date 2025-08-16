package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveRecord

object History {
    private val moves = ArrayDeque<MoveRecord>()

    fun push(selCol: Int, selRow: Int, dstCol: Int, dstRow: Int) {
        val moved = Globals.chessBoard[selRow][selCol].pieceOnBox
        val captured = Globals.chessBoard[dstRow][dstCol].pieceOnBox
        moves.addLast(
            MoveRecord(
                selCol, selRow, dstCol, dstRow,
                moved, captured,
                Globals.whiteCastle,
                Globals.blackCastle,
                Globals.isWhiteMove.value
            )
        )
    }

    fun undo(): Boolean {
        val r = moves.removeLastOrNull() ?: return false
        val G = Globals
        G.chessBoard[r.fromRow][r.fromCol].pieceOnBox = r.movedBefore
        G.chessBoard[r.toRow][r.toCol].pieceOnBox = r.capturedBefore
        G.whiteCastle = r.whiteCastle
        G.blackCastle = r.blackCastle
        G.isWhiteMove.value = r.isWhiteMove
        return true
    }

    fun peek(): MoveRecord? = moves.lastOrNull()
}
package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveRecord

object History {
    var last: MoveRecord? = null

    fun push(selCol: Int, selRow: Int, dstCol: Int, dstRow: Int) {
        val moved = Globals.chessBoard[selRow][selCol].pieceOnBox
        val captured = Globals.chessBoard[dstRow][dstCol].pieceOnBox
        last = MoveRecord(
            selCol, selRow, dstCol, dstRow,
            moved, captured,
            Globals.whiteCastle, Globals.blackCastle,
            Globals.isWhiteMove.value
        )
    }

    fun undo(): Boolean {
        val r = last ?: return false
        Globals.chessBoard[r.fromRow][r.fromCol].pieceOnBox = r.movedBefore
        Globals.chessBoard[r.toRow][r.toCol].pieceOnBox = r.capturedBefore
        Globals.whiteCastle = r.whiteCastle
        Globals.blackCastle = r.blackCastle
        Globals.isWhiteMove.value = r.isWhiteMove
        last = null
        return true
    }
}
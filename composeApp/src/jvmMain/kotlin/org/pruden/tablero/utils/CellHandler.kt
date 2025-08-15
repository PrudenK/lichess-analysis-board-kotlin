package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals

object CellHandler {
    fun setCellsDisable(b: Boolean) {
        for (row in Globals.chessBoard) {
            for (cell in row) {
                cell.disable = b
            }
        }
    }


}

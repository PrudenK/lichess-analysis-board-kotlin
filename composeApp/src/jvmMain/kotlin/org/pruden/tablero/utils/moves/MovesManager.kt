package org.pruden.tablero.utils.moves

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.notation.FenToChessBoard

object MovesManager {

    private val initialPos = Globals.initialFenPos


    fun goToStart() {
        val list = Globals.movesBufferNotation.value
        if (list.isNotEmpty()) list.forEach { it.isActualMove = false }
        FenToChessBoard.setBoardFromFen(initialPos)
        toggleRefresh()
    }

    fun stepBack() {
        val list = Globals.movesBufferNotation.value
        if (list.isEmpty()) {
            FenToChessBoard.setBoardFromFen(initialPos)
            toggleRefresh()
            return
        }
        val idx = list.indexOfFirst { it.isActualMove }
        when {
            idx > 0 -> {
                list[idx].isActualMove = false
                list[idx - 1].isActualMove = true
                FenToChessBoard.setBoardFromFen(list[idx - 1].fen)
            }
            idx == 0 -> {
                list[0].isActualMove = false
                FenToChessBoard.setBoardFromFen(initialPos)
            }
            idx == -1 -> {
                FenToChessBoard.setBoardFromFen(initialPos)
            }
        }
        toggleRefresh()
    }

    fun stepForward() {
        val list = Globals.movesBufferNotation.value
        if (list.isEmpty()) {
            FenToChessBoard.setBoardFromFen(initialPos)
            toggleRefresh()
            return
        }
        val idx = list.indexOfFirst { it.isActualMove }
        when {
            idx == -1 -> {
                list[0].isActualMove = true
                FenToChessBoard.setBoardFromFen(list[0].fen)
            }
            idx < list.lastIndex -> {
                list[idx].isActualMove = false
                list[idx + 1].isActualMove = true
                FenToChessBoard.setBoardFromFen(list[idx + 1].fen)
            }
            else -> {
                FenToChessBoard.setBoardFromFen(list[idx].fen)
            }
        }
        toggleRefresh()
    }

    fun goToEnd() {
        val list = Globals.movesBufferNotation.value
        if (list.isEmpty()) {
            FenToChessBoard.setBoardFromFen(initialPos)
        } else {
            list.forEach { it.isActualMove = false }
            val last = list.last()
            last.isActualMove = true
            FenToChessBoard.setBoardFromFen(last.fen)
        }
        toggleRefresh()
    }

    private fun toggleRefresh() {
        Globals.refreshMovesPanel.value = !Globals.refreshMovesPanel.value
    }
}
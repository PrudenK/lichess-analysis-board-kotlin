package org.pruden.tablero.utils.moves

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.NotationMove
import org.pruden.tablero.utils.notation.FenToChessBoard

object MovesManager {

    private val initialPos = Globals.initialFenPos
    private val list = Globals.movesBufferNotation.value

    fun goToStart() {
        setAllMovesNotActual()
        FenToChessBoard.setBoardFromFen(initialPos)
        toggleRefresh()
    }

    fun stepBack() {
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
        if (list.isEmpty()) {
            FenToChessBoard.setBoardFromFen(initialPos)
        } else {
            setAllMovesNotActual()
            val last = list.last()
            last.isActualMove = true
            FenToChessBoard.setBoardFromFen(last.fen)
        }
        toggleRefresh()
    }

    fun goToClickedMove(move: NotationMove){
        setAllMovesNotActual()

        move.isActualMove = true
        FenToChessBoard.setBoardFromFen(move.fen)
        toggleRefresh()
    }

    private fun setAllMovesNotActual() {
        if (list.isNotEmpty()) list.forEach { it.isActualMove = false }
    }

    private fun toggleRefresh() {
        Globals.refreshMovesPanel.value = !Globals.refreshMovesPanel.value
    }
}
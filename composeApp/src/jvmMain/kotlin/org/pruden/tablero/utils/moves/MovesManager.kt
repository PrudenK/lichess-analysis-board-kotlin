package org.pruden.tablero.utils.moves

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.models.NotationMove
import org.pruden.tablero.utils.notation.FenToChessBoard

object MovesManager {

    private val initialPos = Globals.initialFenPos
    private val list: MutableList<NotationMove> get() = Globals.movesBufferNotation.value
    private val listXX: MutableList<MoveNode> get() = Globals.movesNodesBuffer.value

    fun goToStart() {
        quitAllPossibleMovesMarks()
        setAllMovesNotActual()
        FenToChessBoard.setBoardFromFen(initialPos)
        toggleRefresh()
    }

    fun stepBack() {
        quitAllPossibleMovesMarks()
        if (list.isEmpty()) {
            FenToChessBoard.setBoardFromFen(initialPos)
            toggleRefresh()
            return
        }
        val idx = list.indexOfFirst { it.isActualMove }

        /*
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

         */



        // NODES
        val actualMoveNode = listXX.find { it.isActualMove }!!


        if(actualMoveNode.parentId != null){
            val moveToGo = listXX.find { it.id == actualMoveNode.parentId }!!
            FenToChessBoard.setBoardFromFen(moveToGo.fen, true)

            listXX.forEach { it.isActualMove = false}
            moveToGo.isActualMove = true
        }


        toggleRefresh()
    }

    fun stepForward() {
        quitAllPossibleMovesMarks()
        if (list.isEmpty()) {
            FenToChessBoard.setBoardFromFen(initialPos)
            toggleRefresh()
            return
        }
        val idx = list.indexOfFirst { it.isActualMove }

        /*
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

         */



        // NODES

        val actualMoveNode = listXX.find { it.isActualMove }!!

        if(actualMoveNode.childrenIds.isNotEmpty()){
            val moveToGo = listXX.find { it.id == actualMoveNode.childrenIds[0] }!!
            FenToChessBoard.setBoardFromFen(moveToGo.fen, true)

            listXX.forEach { it.isActualMove = false}
            moveToGo.isActualMove = true
        }


        toggleRefresh()
    }

    fun goToEnd() {
        quitAllPossibleMovesMarks()
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
        quitAllPossibleMovesMarks()
        setAllMovesNotActual()

        move.isActualMove = true
        FenToChessBoard.setBoardFromFen(move.fen)
        toggleRefresh()
    }

    private fun setAllMovesNotActual() {
        if (list.isNotEmpty()) list.forEach { it.isActualMove = false }
    }

    private fun quitAllPossibleMovesMarks() {
        Globals.possibleMoves.value = emptyList()
    }

    private fun toggleRefresh() {
        Globals.refreshMovesPanel.value = !Globals.refreshMovesPanel.value
    }
}
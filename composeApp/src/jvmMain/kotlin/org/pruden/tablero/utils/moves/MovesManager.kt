package org.pruden.tablero.utils.moves

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.notation.FenToChessBoard

object MovesManager {
    private val initialPos = Globals.initialFenPos
    private val list: MutableList<MoveNode> get() = Globals.movesNodesBuffer.value

    fun goToStart() {
        quitAllPossibleMovesMarks()
        setAllMovesNotActual()
        FenToChessBoard.setBoardFromFen(initialPos)
        toggleRefresh()
    }

    fun stepBack() {
        quitAllPossibleMovesMarks()

        // NODES
        val actualMoveNode = list.find { it.isActualMove }!!


        if(actualMoveNode.parentId != null){
            val moveToGo = list.find { it.id == actualMoveNode.parentId }!!
            FenToChessBoard.setBoardFromFen(moveToGo.fen, true)

            list.forEach { it.isActualMove = false}
            moveToGo.isActualMove = true
        }


        toggleRefresh()
    }

    fun stepForward() {
        quitAllPossibleMovesMarks()

        // NODES

        val actualMoveNode = list.find { it.isActualMove }!!

        if(actualMoveNode.childrenIds.isNotEmpty()){
            val moveToGo = list.find { it.id == actualMoveNode.childrenIds[0] }!!
            FenToChessBoard.setBoardFromFen(moveToGo.fen, true)

            list.forEach { it.isActualMove = false}
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

    fun goToClickedMove(move: MoveNode){
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
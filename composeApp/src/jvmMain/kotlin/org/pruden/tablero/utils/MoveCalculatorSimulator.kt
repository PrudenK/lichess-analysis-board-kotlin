package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object MoveCalculatorSimulator {
    private fun calculateCellsControledByAPawnOnBoard(piece: Piece): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val (col, row) = piece.position
        val isWhite = piece.color == Color.White

        if (isWhite) {
            if (col > 0) result.add(Pair(col - 1, row - 1))
            if (col < 7) result.add(Pair(col + 1, row - 1))
        } else {
            if (col > 0) result.add(Pair(col - 1, row + 1))
            if (col < 7) result.add(Pair(col + 1, row + 1))
        }
        return result
    }

    private fun calculateDirectionalMovesOnBoard(
        piece: Piece,
        directions: List<Pair<Int, Int>>,
        maxSteps: Int,
        onlyControlledCells: Boolean = false,
        board: List<List<BoxModel>>
    ): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val (col, row) = piece.position

        for ((dx, dy) in directions) {
            for (step in 1..maxSteps) {
                val newCol = col + dx * step
                val newRow = row + dy * step
                if (newCol !in 0..7 || newRow !in 0..7) break
                val target = board[newRow][newCol].pieceOnBox
                if (target == null) {
                    result.add(Pair(newCol, newRow))
                } else {
                    if (target.color != piece.color || onlyControlledCells) {
                        result.add(Pair(newCol, newRow))
                    }
                    break
                }
            }
        }
        return result
    }

    private fun calculateKnightMovesOnBoard(piece: Piece, board: List<List<BoxModel>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val (col, row) = piece.position
        val moves = Globals.knightDirections.map { Pair(col + it.first, row + it.second) }
        for ((c, r) in moves) {
            if (c in 0..7 && r in 0..7) {
                val target = board[r][c].pieceOnBox
                if (target == null || target.color != piece.color) {
                    result.add(Pair(c, r))
                }
            }
        }
        return result
    }

    fun calculateCellsControledByOponentOnBoard(board: List<List<BoxModel>>, color: Color): MutableSet<Pair<Int, Int>> {
        val cells = mutableSetOf<Pair<Int, Int>>()
        for (row in 0..7) {
            for (col in 0..7) {
                val piece = board[row][col].pieceOnBox ?: continue
                if (piece.color != color) {
                    val moves = when (piece.type) {
                        PieceType.Pawn -> calculateCellsControledByAPawnOnBoard(piece)
                        PieceType.Rook -> calculateDirectionalMovesOnBoard(piece, Globals.rookDirections, 7, true, board)
                        PieceType.Bishop -> calculateDirectionalMovesOnBoard(piece, Globals.bishopDirections, 7, true, board)
                        PieceType.Queen -> calculateDirectionalMovesOnBoard(piece, Globals.rookDirections + Globals.bishopDirections, 7, true, board)
                        PieceType.King -> calculateDirectionalMovesOnBoard(piece, Globals.rookDirections + Globals.bishopDirections, 1, true, board)
                        PieceType.Knight -> calculateKnightMovesOnBoard(piece, board)
                        else -> listOf()
                    }
                    cells.addAll(moves)
                }
            }
        }
        return cells
    }

}
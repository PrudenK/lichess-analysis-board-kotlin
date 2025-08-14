package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import kotlin.math.log

object MoveCalculator {

    fun getPosibleMoves(piece: Piece): List<Pair<Int, Int>> {
        return when (piece.type) {
            PieceType.Pawn -> calculatePawnMoves(piece)
            PieceType.Rook -> calculateDirectionalMoves(piece, listOf(
                Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)
            ), 7)
            PieceType.Bishop -> calculateDirectionalMoves(piece, listOf(
                Pair(1, 1), Pair(-1, -1), Pair(-1, 1), Pair(1, -1)
            ), 7)
            PieceType.Queen -> calculateDirectionalMoves(piece, listOf(
                Pair(1, 1), Pair(-1, -1), Pair(-1, 1), Pair(1, -1),
                Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)
            ), 7)
            PieceType.King -> calculateDirectionalMoves(piece, listOf(
                Pair(1, 1), Pair(-1, -1), Pair(-1, 1), Pair(1, -1),
                Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1)
            ), 1)
            PieceType.Knight -> calculateKnightMoves(piece)
            else -> emptyList()
        }
    }

    private fun calculatePawnMoves(piece: Piece): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        val isBlack = piece.color == Color.Black
        val isFirstMove = if (isBlack) row == 1 else row == 6

        logs(piece, col, row)

        if (isBlack) {
            if (row + 1 <= 7 && isFreeCell(col, row + 1)) {
                result.add(Pair(col, row + 1))
                if (isFirstMove && row + 2 <= 7 && isFreeCell(col, row + 2)) {
                    result.add(Pair(col, row + 2))
                }
            }
            if (col + 1 <= 7 && row + 1 <= 7) {
                val pieceToCapture = Globals.chessBoard[row + 1][col + 1].pieceOnBox
                if (pieceToCapture != null && pieceToCapture.color == Color.White) {
                    result.add(Pair(col + 1, row + 1))
                }
            }
            if (col - 1 >= 0 && row + 1 <= 7) {
                val pieceToCapture = Globals.chessBoard[row + 1][col - 1].pieceOnBox
                if (pieceToCapture != null && pieceToCapture.color == Color.White) {
                    result.add(Pair(col - 1, row + 1))
                }
            }
        } else {
            if (row - 1 >= 0 && isFreeCell(col, row - 1)) {
                result.add(Pair(col, row - 1))
                if (isFirstMove && row - 2 >= 0 && isFreeCell(col, row - 2)) {
                    result.add(Pair(col, row - 2))
                }
            }
            if (col + 1 <= 7 && row - 1 >= 0) {
                val pieceToCapture = Globals.chessBoard[row - 1][col + 1].pieceOnBox
                if (pieceToCapture != null && pieceToCapture.color == Color.Black) {
                    result.add(Pair(col + 1, row - 1))
                }
            }
            if (col - 1 >= 0 && row - 1 >= 0) {
                val pieceToCapture = Globals.chessBoard[row - 1][col - 1].pieceOnBox
                if (pieceToCapture != null && pieceToCapture.color == Color.Black) {
                    result.add(Pair(col - 1, row - 1))
                }
            }
        }

        return result
    }

    private fun calculateDirectionalMoves(piece: Piece, directions: List<Pair<Int, Int>>, maxSteps: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val (col, row) = piece.position

        logs(piece, col, row)

        for ((dx, dy) in directions) {
            for (step in 1..maxSteps) {
                try {
                    val newCol = col + dx * step
                    val newRow = row + dy * step
                    if (isFreeCell(newCol, newRow)) {
                        result.add(Pair(newCol, newRow))
                    } else {
                        if (Globals.chessBoard[newRow][newCol].pieceOnBox?.color != piece.color) {
                            result.add(Pair(newCol, newRow))
                        }
                        break
                    }
                } catch (_: Exception) {}
            }
        }
        return result
    }

    private fun calculateKnightMoves(piece: Piece): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        logs(piece, col, row)

        val directions = listOf(
            Pair(-2, -1),
            Pair(-1, -2),
            Pair(1, -2),
            Pair(-2, 1),
            Pair(2, -1),
            Pair(2, 1),
            Pair(1, 2),
            Pair(-1, 2)
        )

        for ((dx, dy) in directions) {
            try {
                val newCol = col + dx
                val newRow = row + dy
                if (isFreeCell(newCol, newRow)) {
                    result.add(Pair(newCol, newRow))
                } else {
                    if (Globals.chessBoard[newRow][newCol].pieceOnBox?.color != piece.color) {
                        result.add(Pair(newCol, newRow))
                    }
                }
            } catch (_: Exception) {}
        }

        return result
    }

    private fun isFreeCell(col: Int, row: Int): Boolean {
        return Globals.chessBoard[row][col].pieceOnBox == null
    }

    private fun logs(
        piece: Piece,
        col: Int,
        row: Int,
    ){
        println("${piece.id}  Columna(X):$col   Fila(Y):$row")
        for (y in 0..7) {
            for (x in 0..7) {
                val imprimir = Globals.chessBoard[y][x].pieceOnBox?.type ?: "null"
                val color = if(Globals.chessBoard[y][x].pieceOnBox?.color == Color.Black) "B" else if (Globals.chessBoard[y][x].pieceOnBox?.color == Color.White) "W" else "X"
                print(String.format("%-8s", "$imprimir $color"))
            }
            println()
        }
    }
}

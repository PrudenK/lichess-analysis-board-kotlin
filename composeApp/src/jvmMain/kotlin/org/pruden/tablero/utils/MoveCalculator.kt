package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object MoveCalculator {

    fun getPosibleMoves(piece: Piece): List<Pair<Int, Int>> {
        return when (piece.type) {
            PieceType.Pawn -> calculatePawnMoves(piece)
            PieceType.Rook -> calculateRookMoves(piece)
            PieceType.Bishop -> calculateBishopMoves(piece)
            PieceType.Queen -> calculateQueenMoves(piece)
            else -> emptyList()
        }
    }

    private fun calculatePawnMoves(piece: Piece): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        val isBlack = piece.color == Color.Black
        val isFirstMove = if (isBlack) row == 1 else row == 6

        println("${piece.id}  Columna(X):$col   Fila(Y):$row")
        for (y in 0..7) {
            for (x in 0..7) {
                val imprimir = Globals.chessBoard[y][x].pieceOnBox?.type ?: "null"
                print(String.format("%-8s", imprimir))
            }
            println()
        }

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

    private fun calculateRookMoves(piece: Piece): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        println("${piece.id}  Columna(X):$col   Fila(Y):$row")
        for (y in 0..7) {
            for (x in 0..7) {
                val imprimir = Globals.chessBoard[y][x].pieceOnBox?.type ?: "null"
                val color = if(Globals.chessBoard[y][x].pieceOnBox?.color == Color.Black) "B" else if (Globals.chessBoard[y][x].pieceOnBox?.color == Color.White) "W" else "X"
                print(String.format("%-8s", "$imprimir $color"))
            }
            println()
        }


        val directions = listOf(
            Pair(1, 0),
            Pair(-1, 0),
            Pair(0, 1),
            Pair(0, -1)
        )

        for ((dx, dy) in directions) {
            for (step in 1..7) {
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

    private fun calculateBishopMoves(piece: Piece): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        println("${piece.id}  Columna(X):$col   Fila(Y):$row")
        for (y in 0..7) {
            for (x in 0..7) {
                val imprimir = Globals.chessBoard[y][x].pieceOnBox?.type ?: "null"
                val color = if(Globals.chessBoard[y][x].pieceOnBox?.color == Color.Black) "B" else if (Globals.chessBoard[y][x].pieceOnBox?.color == Color.White) "W" else "X"
                print(String.format("%-8s", "$imprimir $color"))
            }
            println()
        }

        val directions = listOf(
            Pair(1, 1),
            Pair(-1, -1),
            Pair(-1, 1),
            Pair(1, -1)
        )

        for ((dx, dy) in directions) {
            for (step in 1..7) {
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

    private fun calculateQueenMoves(piece: Piece): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        println("${piece.id}  Columna(X):$col   Fila(Y):$row")
        for (y in 0..7) {
            for (x in 0..7) {
                val imprimir = Globals.chessBoard[y][x].pieceOnBox?.type ?: "null"
                val color = if(Globals.chessBoard[y][x].pieceOnBox?.color == Color.Black) "B" else if (Globals.chessBoard[y][x].pieceOnBox?.color == Color.White) "W" else "X"
                print(String.format("%-8s", "$imprimir $color"))
            }
            println()
        }

        val directions = listOf(
            Pair(1, 1),
            Pair(-1, -1),
            Pair(-1, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(-1, 0),
            Pair(0, 1),
            Pair(0, -1)
        )

        for ((dx, dy) in directions) {
            for (step in 1..7) {
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

        private fun isFreeCell(col: Int, row: Int): Boolean {
        return Globals.chessBoard[row][col].pieceOnBox == null
    }
}

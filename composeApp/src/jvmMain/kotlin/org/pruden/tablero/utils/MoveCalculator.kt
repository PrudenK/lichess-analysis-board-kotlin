package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object MoveCalculator {

    private val knightDirections = listOf(
        Pair(-2, -1),
        Pair(-1, -2),
        Pair(1, -2),
        Pair(-2, 1),
        Pair(2, -1),
        Pair(2, 1),
        Pair(1, 2),
        Pair(-1, 2)
    )

    private val rookDirections = listOf(
        Pair(1, 0),
        Pair(-1, 0),
        Pair(0, 1),
        Pair(0, -1)
    )

    private val bishopDirections = listOf(
        Pair(1, 1),
        Pair(-1, -1),
        Pair(-1, 1),
        Pair(1, -1)
    )

    fun getPosibleMoves(piece: Piece): List<Pair<Int, Int>> {
        return when (piece.type) {
            PieceType.Pawn -> calculatePawnMoves(piece)
            PieceType.Rook -> calculateDirectionalMoves(piece, rookDirections, 7)
            PieceType.Bishop -> calculateDirectionalMoves(piece, bishopDirections, 7)
            PieceType.Queen -> calculateDirectionalMoves(piece, rookDirections + bishopDirections, 7)
            PieceType.King -> calculateKingMoves(piece)
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

    private fun calculateCellsControledByAPawn(piece: Piece): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        val (col, row) = piece.position
        val isWhite = piece.color == Color.White

        if(isWhite){
            if(col == 0) {
                result.add(Pair(col + 1, row - 1))
            }else if(col == 7){
                result.add(Pair(col - 1, row - 1))
            }else{
                result.add(Pair(col + 1, row - 1))
                result.add(Pair(col - 1, row - 1))
            }
        }else{
            if(col == 0) {
                result.add(Pair(col + 1, row + 1))
            }else if(col == 7){
                result.add(Pair(col - 1, row + 1))
            }else{
                result.add(Pair(col + 1, row + 1))
                result.add(Pair(col - 1, row + 1))
            }
        }

        return result
    }

    private fun calculateDirectionalMoves(piece: Piece, directions: List<Pair<Int, Int>>, maxSteps: Int, onlyControlledCells : Boolean = false): List<Pair<Int, Int>> {
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
                        if (Globals.chessBoard[newRow][newCol].pieceOnBox?.color != piece.color || onlyControlledCells) {
                            result.add(Pair(newCol, newRow))
                        }
                        break
                    }
                } catch (_: Exception) {}
            }
        }
        return result
    }

    private fun calculateKingMoves(piece : Piece): List<Pair<Int, Int>> {
        val cellsControledByOtherPieces = mutableSetOf<Pair<Int, Int>>()

        for(i in 0..7){
            for (j in 0..7) {

                if(!Globals.chessBoard[i][j].isFreeCell() &&
                    Globals.chessBoard[i][j].pieceOnBox?.color != piece.color
                    ){

                    val currentPiece = Globals.chessBoard[i][j].pieceOnBox!!
                    println(currentPiece.type.toString() + " " + currentPiece.color+ " " + piece.color+ "  "+ currentPiece.position)


                    val directions = when(currentPiece.type){
                        PieceType.Pawn -> calculateCellsControledByAPawn(currentPiece)
                        PieceType.Rook -> calculateDirectionalMoves(currentPiece, rookDirections, 7, true)
                        PieceType.Bishop -> calculateDirectionalMoves(currentPiece, bishopDirections, 7, true)
                        PieceType.Queen -> calculateDirectionalMoves(currentPiece, rookDirections + bishopDirections, 7, true)
                        PieceType.King -> calculateDirectionalMoves(currentPiece, rookDirections + bishopDirections, 1, true)
                        PieceType.Knight -> calculateKnightMoves(currentPiece)
                        else -> {listOf(Pair(-1, -1))}
                    }

                    cellsControledByOtherPieces.addAll(directions)
                }
            }
        }

        val blackKingMoves = calculateDirectionalMoves(piece, rookDirections + bishopDirections, 1).toMutableList()

        // 0-0
        if(piece.color == Color.White){
            if(!Globals.whiteCastle.second){
                if(!Globals.whiteCastle.third){
                    if(Globals.chessBoard[7][5].isFreeCell() &&
                        Globals.chessBoard[7][6].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(5, 7))
                        )
                    blackKingMoves += Pair(6, 7)
                }
            }
        }else{
            if(!Globals.blackCastle.second){
                if(!Globals.blackCastle.third){
                    if(Globals.chessBoard[0][5].isFreeCell() &&
                        Globals.chessBoard[0][6].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(5, 0))

                        )
                        blackKingMoves += Pair(6, 0)
                }
            }
        }

        // 0-0-0
        if(piece.color == Color.White){
            if(!Globals.whiteCastle.second){
                if(!Globals.whiteCastle.first){
                    if(Globals.chessBoard[7][3].isFreeCell() &&
                        Globals.chessBoard[7][2].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(2, 7))
                    )
                        blackKingMoves += Pair(2, 7)
                }
            }
        }else{
            if(!Globals.blackCastle.second){
                if(!Globals.blackCastle.first){
                    if(Globals.chessBoard[0][3].isFreeCell() &&
                        Globals.chessBoard[0][2].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(2, 0))

                    )
                        blackKingMoves += Pair(2, 0)
                }
            }
        }


        return blackKingMoves - cellsControledByOtherPieces
    }

    private fun calculateKnightMoves(piece: Piece): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        logs(piece, col, row)

        for ((dx, dy) in knightDirections) {
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

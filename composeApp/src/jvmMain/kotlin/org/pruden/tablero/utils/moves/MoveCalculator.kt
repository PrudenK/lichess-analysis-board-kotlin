package org.pruden.tablero.utils.moves

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.castle.CastleHandler

object MoveCalculator {
    fun getPosibleMoves(piece: Piece): List<Pair<Int, Int>> {
        val moves = when (piece.type) {
            PieceType.Pawn -> calculatePawnMoves(piece)
            PieceType.Rook -> calculateDirectionalMoves(piece, Globals.rookDirections, 7)
            PieceType.Bishop -> calculateDirectionalMoves(piece, Globals.bishopDirections, 7)
            PieceType.Queen -> calculateDirectionalMoves(piece, Globals.rookDirections + Globals.bishopDirections, 7)
            PieceType.King -> calculateKingMoves(piece)
            PieceType.Knight -> calculateKnightMoves(piece)
            else -> emptyList()
        }

        if ((piece.color == Color.White && Globals.whiteIsChecked.value) ||
            (piece.color == Color.Black && Globals.blackIsChecked.value)
        ) {
            return moves.filter { move ->
                !wouldKingBeInCheckAfterMove(piece, move)
            }
        }

        // Filtra las clavadas
        if (piece.type != PieceType.King) {
            return moves.filter { move -> !wouldKingBeInCheckAfterMove(piece, move) }
        }

        return moves
    }

    private fun calculatePawnMoves(piece: Piece): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        val isBlack = piece.color == Color.Black
        val isFirstMove = if (isBlack) row == 1 else row == 6

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

            if(row == 4 && Globals.posiblePassant){
                if(col == Globals.colPassant + 1 || col == Globals.colPassant -1){
                    val enPassantCell = Pair(Globals.colPassant, row + 1)
                    result.add(enPassantCell)
                    Globals.enPassantCell = enPassantCell
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

            if(row == 3 && Globals.posiblePassant){
                if(col == Globals.colPassant + 1 || col == Globals.colPassant -1){
                    val enPassantCell = Pair(Globals.colPassant, row - 1)
                    result.add(enPassantCell)
                    Globals.enPassantCell = enPassantCell
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
                        if(!(onlyControlledCells && Globals.chessBoard[newRow][newCol].pieceOnBox?.type == PieceType.King)) { // for -> Rook Kink freeCell(X) (X is not available)
                            break
                        }
                    }
                } catch (_: Exception) {}
            }
        }
        return result
    }

    private fun calculateKingMoves(piece : Piece): List<Pair<Int, Int>> {
        val cellsControledByOtherPieces = calculateCellsControledByOponent(piece.color)

        val miKingMoves = calculateDirectionalMoves(piece, Globals.rookDirections + Globals.bishopDirections, 1).toMutableList()

        if ((piece.color == Color.White && !Globals.whiteIsChecked.value) ||
            (piece.color == Color.Black && !Globals.blackIsChecked.value)
        ){
            CastleHandler.addShortCastleMoveIfAvailable(piece, cellsControledByOtherPieces, miKingMoves)
            CastleHandler.addLongCastleMoveIfAvailable(piece, cellsControledByOtherPieces, miKingMoves)
        }

        return miKingMoves - cellsControledByOtherPieces
    }

    private fun calculateKnightMoves(piece: Piece): List<Pair<Int, Int>>{
        val result = mutableListOf<Pair<Int, Int>>()

        val col = piece.position.first
        val row = piece.position.second

        for ((dx, dy) in Globals.knightDirections) {
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

    fun calculateCellsControledByOponent(color: Color): MutableSet<Pair<Int, Int>>{
        val cellsControledByOtherPieces = mutableSetOf<Pair<Int, Int>>()

        for(i in 0..7){
            for (j in 0..7) {

                if(!Globals.chessBoard[i][j].isFreeCell() &&
                    Globals.chessBoard[i][j].pieceOnBox?.color != color
                ){

                    val currentPiece = Globals.chessBoard[i][j].pieceOnBox!!

                    val directions = when(currentPiece.type){
                        PieceType.Pawn -> calculateCellsControledByAPawn(currentPiece)
                        PieceType.Rook -> calculateDirectionalMoves(currentPiece, Globals.rookDirections, 7, true)
                        PieceType.Bishop -> calculateDirectionalMoves(currentPiece, Globals.bishopDirections, 7, true)
                        PieceType.Queen -> calculateDirectionalMoves(currentPiece, Globals.rookDirections + Globals.bishopDirections, 7, true)
                        PieceType.King -> calculateDirectionalMoves(currentPiece, Globals.rookDirections + Globals.bishopDirections, 1, true)
                        PieceType.Knight -> calculateKnightMoves(currentPiece)
                        else -> {listOf(Pair(-1, -1))}
                    }

                    cellsControledByOtherPieces.addAll(directions)
                }
            }
        }
        return cellsControledByOtherPieces
    }

    private fun wouldKingBeInCheckAfterMove(piece: Piece, move: Pair<Int, Int>): Boolean {
        // Copiar tablero (clonado profundo de BoxModel y Piece)
        val boardCopy = Globals.chessBoard.map { row ->
            row.map { it.copy(pieceOnBox = it.pieceOnBox?.copy(position = it.pieceOnBox!!.position.copy())) }.toMutableList()
        }.toMutableList()

        // Posiciones inicial y final
        val (fromCol, fromRow) = piece.position
        val (toCol, toRow) = move

        // Mover pieza en la copia
        boardCopy[toRow][toCol].pieceOnBox = boardCopy[fromRow][fromCol].pieceOnBox
        boardCopy[toRow][toCol].pieceOnBox?.position = Pair(toCol, toRow)
        boardCopy[fromRow][fromCol].pieceOnBox = null

        // Encontrar rey del mismo color
        val king = boardCopy.flatten().first { it.pieceOnBox?.color == piece.color && it.pieceOnBox?.type == PieceType.King }.pieceOnBox!!

        // Calcular casillas controladas por el oponente en la copia
        val cellsControlled = MoveCalculatorSimulator.calculateCellsControledByOponentOnBoard(
            boardCopy,
            if (piece.color == Color.White) Color.White else Color.Black
        )

        // Ver si el rey sigue en jaque
        return cellsControlled.contains(king.position)
    }

    fun getAllPossibleMovesByColor(color: Color): List<Pair<Int, Int>> {
        val moves = mutableListOf<Pair<Int, Int>>()

        for(i in 0..7){
            for (j in 0..7) {
                if(!Globals.chessBoard[i][j].isFreeCell() &&
                    Globals.chessBoard[i][j].pieceOnBox?.color == color
                ) {
                    moves += getPosibleMoves(Globals.chessBoard[i][j].pieceOnBox!!)
                }
            }
        }

        return moves
    }


    private fun isFreeCell(col: Int, row: Int): Boolean {
        return Globals.chessBoard[row][col].pieceOnBox == null
    }
}

package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.*
import org.pruden.tablero.utils.chessBoard.CellHandler
import org.pruden.tablero.utils.moves.MoveCalculator
import kotlin.math.abs

object NotationHandler {
    lateinit var boardBeforeMoves: Array<Array<BoxModel>>

    fun addMoveToBuffer(piece: Piece, fromCell: String, clickedCell: BoxModel) {
        val from = Globals.lastPieceStartPos
        val to = piece.position
        val capture = isCapture(piece, from, to, clickedCell)



        if (capture || piece.type == PieceType.Pawn) {
            Globals.halfMoves = 0
        }else{
            Globals.halfMoves += 1
        }


        castleSanIfAny(piece, from, to)?.let {
            NotationMovesHandler.addNotationMove(it); return
        }

        val san = if (piece.type == PieceType.Pawn) {
            val dest = square(to)
            if (capture) "${fromCell[0]}x$dest" else dest
        } else {
            val letter = letterOfAPiece(piece)
            val dest = square(to)
            val cap = if (capture) "x" else ""
            val dis = disambiguation(piece, fromCell)

            "$letter$dis$cap$dest"
        }

        NotationMovesHandler.addNotationMove(san)
    }

    fun appendPromotion(type: PieceType) {
        val t = when (type) {
            PieceType.Queen -> "Q"
            PieceType.Rook -> "R"
            PieceType.Bishop -> "B"
            PieceType.Knight -> "N"
            else -> return
        }
        NotationMovesHandler.modifyLastMoveNotation("=$t")
    }

    fun annotateCheckIfAny(promoted: Boolean = false) {
        val side = if (if(promoted) !Globals.isWhiteMove.value else Globals.isWhiteMove.value) Color.White else Color.Black
        val attacked = MoveCalculator.calculateCellsControledByOponent(side)
        val king = CellHandler.getKingByColor(side)
        if (attacked.contains(king.position)) appendCheck()
    }
    private fun appendCheck() {
        NotationMovesHandler.modifyLastMoveNotation("+")
    }

    fun appendMate() {
        NotationMovesHandler.modifyLastMoveNotation("#")
    }

    fun removeLastMove() = Globals.movesBufferNotation.value.removeLast()

    private fun letterOfAPiece(piece: Piece): String = when (piece.type) {
        PieceType.King -> "K"
        PieceType.Queen -> "Q"
        PieceType.Rook -> "R"
        PieceType.Bishop -> "B"
        PieceType.Knight -> "N"
        else -> ""
    }

    private fun castleSanIfAny(piece: Piece, from: Pair<Int,Int>, to: Pair<Int,Int>): String? {
        if (piece.type != PieceType.King) return null
        val dx = to.first - from.first
        if (abs(dx) != 2) return null
        return if (dx == 2) "O-O" else "O-O-O"
    }

    private fun isCapture(piece: Piece, from: Pair<Int,Int>, to: Pair<Int,Int>, clickedCell: BoxModel): Boolean {
        if (clickedCell.pieceOnBox != null) return true
        if (piece.type == PieceType.Pawn && from.first != to.first) return true
        return false
    }

    private fun disambiguation(piece: Piece, fromCell: String): String {
        var dis = ""
        val pieces = CellHandler.findPiecesInBoard(piece, boardBeforeMoves)

        if(pieces.isNotEmpty()) {
            val busyRow = pieces.any{
                MoveCalculator.getPosibleMoves(it, piece, chessBoard = boardBeforeMoves)
                    .contains(piece.position)
                        && fileChar(it.position.first) == fromCell[0]
            }

            val busyCol = pieces.any{
                MoveCalculator.getPosibleMoves(it, piece, chessBoard = boardBeforeMoves)
                    .contains(piece.position)
                        && rankChar(it.position.second) == fromCell[1]
            }

            if(!busyCol && !busyRow){
                val busyCell = pieces.find{
                    MoveCalculator.getPosibleMoves(it, piece, chessBoard = boardBeforeMoves).contains(piece.position)
                }

                var d = ""
                if(busyCell != null){
                    d = fromCell[0].toString()
                }

                dis = d
            }else{
                dis = "${if(busyCol) fromCell[0] else ""}${if(busyRow) fromCell[1] else ""}"
            }
        }

        return dis
    }

    private fun square(pos: Pair<Int,Int>) = "${fileChar(pos.first)}${rankChar(pos.second)}"
    private fun fileChar(col: Int) = ('a'.code + col).toChar()
    private fun rankChar(row: Int) = (8 - row).digitToChar()
}

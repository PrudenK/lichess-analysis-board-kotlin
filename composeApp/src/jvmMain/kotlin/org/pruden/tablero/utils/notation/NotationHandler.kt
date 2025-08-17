package org.pruden.tablero.utils.notation

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.chessBoard.CellHandler
import org.pruden.tablero.utils.moves.MoveCalculator
import kotlin.math.abs

object NotationHandler {

    fun addMoveToBuffer(piece: Piece, fromCell: String, clickedCell: BoxModel) {
        val from = Globals.lastPieceStartPos
        val to = piece.position
        castleSanIfAny(piece, from, to)?.let {
            Globals.movesBuffer.value.add(it); return
        }
        val capture = isCapture(piece, from, to, clickedCell)
        val san = if (piece.type == PieceType.Pawn) {
            val dest = square(to)
            if (capture) "${fromCell[0]}x$dest" else dest
        } else {
            val letter = letterOfAPiece(piece)
            val dis = disambiguation(piece, to, from)
            val dest = square(to)
            val cap = if (capture) "x" else ""
            "$letter$dis$cap$dest"
        }
        Globals.movesBuffer.value.add(san)
    }

    fun appendPromotion(type: PieceType) {
        if (Globals.movesBuffer.value.isEmpty()) return
        val t = when (type) {
            PieceType.Queen -> "Q"
            PieceType.Rook -> "R"
            PieceType.Bishop -> "B"
            PieceType.Knight -> "N"
            else -> return
        }
        val last = Globals.movesBuffer.value.removeLast()
        Globals.movesBuffer.value.add("$last=$t")
    }

    fun annotateCheckIfAny() {
        val side = if (Globals.isWhiteMove.value) Color.White else Color.Black
        val attacked = MoveCalculator.calculateCellsControledByOponent(side)
        val king = CellHandler.getKingByColor(side)
        if (attacked.contains(king.position)) appendCheck()
    }
    private fun appendCheck() {
        if (Globals.movesBuffer.value.isEmpty()) return
        val last = Globals.movesBuffer.value.removeLast()
        Globals.movesBuffer.value.add("$last+")
    }

    fun appendMate() {
        if (Globals.movesBuffer.value.isEmpty()) return
        val last = Globals.movesBuffer.value.removeLast().replace("+", "")
        Globals.movesBuffer.value.add("$last#")
    }

    fun removeLastMove() = Globals.movesBuffer.value.removeLast()

    fun letterOfAPiece(piece: Piece): String = when (piece.type) {
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


    // TODO: Para mañana
    private fun disambiguation(piece: Piece, to: Pair<Int,Int>, from: Pair<Int,Int>): String {
        val candidates = mutableListOf<Pair<Int,Int>>()
        for (r in 0..7) for (c in 0..7) {
            val p = Globals.chessBoard[r][c].pieceOnBox ?: continue
            if (p === piece) continue
            if (p.color != piece.color) continue
            if (p.type != piece.type) continue
            if (MoveCalculator.getPosibleMoves(p).contains(to)) candidates.add(Pair(c, r))
        }
        if (candidates.isEmpty()) return ""
        val shareFile = candidates.any { it.first == from.first }
        val shareRank = candidates.any { it.second == from.second }
        return when {
            !shareFile -> fileChar(from.first).toString()
            !shareRank -> rankChar(from.second).toString()
            else -> fileChar(from.first).toString() + rankChar(from.second)
        }
    }


    private fun square(pos: Pair<Int,Int>) = "${fileChar(pos.first)}${rankChar(pos.second)}"
    private fun fileChar(col: Int) = ('a'.code + col).toChar()
    private fun rankChar(row: Int) = (8 - row).digitToChar()
}

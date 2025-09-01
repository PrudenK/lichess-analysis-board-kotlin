package org.pruden.tablero.utils.promotion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.LastMove
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.moves.History
import org.pruden.tablero.utils.nodes.MovesNodesManager
import org.pruden.tablero.utils.notation.NotationHandler
import org.pruden.tablero.utils.result.ResultHandler

object PromotionHandler {
    var selCol = -1

    @Composable
    fun applyPromotionFilter() {
        if (Globals.isWhitePromotion.value || Globals.isBlackPromotion.value) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }
    }

    fun verifyPromotion(movedPiece: Piece) {
        if (movedPiece.type == PieceType.Pawn) {
            if (movedPiece.color == org.pruden.tablero.models.Color.White) {
                if (movedPiece.positionToChessNotation().contains("8")) {
                    Globals.promotionCol = movedPiece.position.first
                    Globals.isWhitePromotion.value = true
                }
            } else {
                if (movedPiece.positionToChessNotation().contains("1")) {
                    Globals.promotionCol = movedPiece.position.first
                    Globals.isBlackPromotion.value = true
                }
            }
        }
    }

    fun handlePromotionClick(clickedRow: Int, clickedCol: Int) : Boolean {
        var promoteCanceled = false
        if (Globals.isWhitePromotion.value) {
            promoteCanceled = applyPromotionChoiceWhite(clickedRow, clickedCol)
        } else if (Globals.isBlackPromotion.value) {
            promoteCanceled = applyPromotionChoiceBlack(clickedRow, clickedCol)
        }

        return promoteCanceled
    }

    private fun applyPromotionChoiceWhite(clickedRow: Int, clickedCol: Int): Boolean {
        val promotionCol = Globals.promotionCol
        val pawnPromoted = Globals.pawnPromoted!!
        var promoteCanceled = false
        var promotionWithCapture = true

        if(clickedRow > 3 || clickedCol != promotionCol) {
            if(promotionCol == selCol) promotionWithCapture = false

            History.undo()
            Globals.lastMove.value = History.peek()?.let {
                LastMove(from = Pair(it.fromCol, it.fromRow), to = Pair(it.toCol, it.toRow))
            }
            pawnPromoted.position = Globals.lastPieceStartPos
            promoteCanceled = true

            NotationHandler.removeLastMove()
        }else{
            val piecePromoted = when (clickedRow) {
                1 -> PieceProvider.getWhiteKnight(pawnPromoted.position)
                2 -> PieceProvider.getWhiteRook(pawnPromoted.position)
                3 -> PieceProvider.getWhiteBishop(pawnPromoted.position)
                else -> PieceProvider.getWhiteQueen(pawnPromoted.position)
            }
            Globals.chessBoard[0][promotionCol].pieceOnBox = piecePromoted



            MovesNodesManager.addMove(
                san = Globals.nodePromotion!!.san,
                to = Globals.nodePromotion!!.to,
                from = Globals.nodePromotion!!.from
            )



            NotationHandler.appendPromotion(piecePromoted.type)

        }
        if(promotionWithCapture) {
            Globals.chessBoard[1][promotionCol].pieceOnBox = Globals.promotionBuffer[0]
        }

        Globals.chessBoard[2][promotionCol].pieceOnBox = Globals.promotionBuffer[1]
        Globals.chessBoard[3][promotionCol].pieceOnBox = Globals.promotionBuffer[2]
        Globals.promotionBuffer.clear()

        Globals.isWhitePromotion.value = false
        return promoteCanceled
    }

    private fun applyPromotionChoiceBlack(clickedRow: Int, clickedCol: Int): Boolean {
        val promotionCol = Globals.promotionCol
        val pawnPromoted = Globals.pawnPromoted!!
        var promoteCanceled = false
        var promotionWithCapture = true

        if(clickedRow < 4 || clickedCol != promotionCol) {
            if(promotionCol == selCol) promotionWithCapture = false

            History.undo()
            Globals.lastMove.value = History.peek()?.let {
                LastMove(from = Pair(it.fromCol, it.fromRow), to = Pair(it.toCol, it.toRow))
            }

            pawnPromoted.position = Globals.lastPieceStartPos
            promoteCanceled = true

            NotationHandler.removeLastMove()
        }else{
            val piecePromoted = when (clickedRow) {
                6 -> PieceProvider.getBlackKnight(pawnPromoted.position)
                5 -> PieceProvider.getBlackRook(pawnPromoted.position)
                4 -> PieceProvider.getBlackBishop(pawnPromoted.position)
                else -> PieceProvider.getBlackQueen(pawnPromoted.position)
            }
            Globals.chessBoard[7][promotionCol].pieceOnBox = piecePromoted


            MovesNodesManager.addMove(
                san = Globals.nodePromotion!!.san,
                to = Globals.nodePromotion!!.to,
                from = Globals.nodePromotion!!.from
            )


            NotationHandler.appendPromotion(piecePromoted.type)
        }

        if(promotionWithCapture){
            Globals.chessBoard[6][promotionCol].pieceOnBox = Globals.promotionBuffer[0]
        }
        Globals.chessBoard[5][promotionCol].pieceOnBox = Globals.promotionBuffer[1]
        Globals.chessBoard[4][promotionCol].pieceOnBox = Globals.promotionBuffer[2]
        Globals.promotionBuffer.clear()

        Globals.isBlackPromotion.value = false
        return promoteCanceled
    }

    fun preparePromotionSelectionWhite(movedPiece: Piece) {
        val promotionCol = Globals.promotionCol

        val c1 = Globals.chessBoard[0][promotionCol]
        val c2 = Globals.chessBoard[1][promotionCol]
        val c3 = Globals.chessBoard[2][promotionCol]
        val c4 = Globals.chessBoard[3][promotionCol]

        c1.disable = false
        c2.disable = false
        c3.disable = false
        c4.disable = false

        Globals.promotionBuffer.add(c2.pieceOnBox)
        Globals.promotionBuffer.add(c3.pieceOnBox)
        Globals.promotionBuffer.add(c4.pieceOnBox)

        c1.pieceOnBox = PieceProvider.getWhiteQueen(movedPiece.position)
        c2.pieceOnBox = PieceProvider.getWhiteKnight(movedPiece.position)
        c3.pieceOnBox = PieceProvider.getWhiteRook(movedPiece.position)
        c4.pieceOnBox = PieceProvider.getWhiteBishop(movedPiece.position)

        Globals.pawnPromoted = movedPiece
    }

    fun preparePromotionSelectionBlack(movedPiece: Piece) {
        val promotionCol = Globals.promotionCol

        val c1 = Globals.chessBoard[7][promotionCol]
        val c2 = Globals.chessBoard[6][promotionCol]
        val c3 = Globals.chessBoard[5][promotionCol]
        val c4 = Globals.chessBoard[4][promotionCol]

        c1.disable = false
        c2.disable = false
        c3.disable = false
        c4.disable = false

        Globals.promotionBuffer.add(c2.pieceOnBox)
        Globals.promotionBuffer.add(c3.pieceOnBox)
        Globals.promotionBuffer.add(c4.pieceOnBox)

        c1.pieceOnBox = PieceProvider.getBlackQueen(movedPiece.position)
        c2.pieceOnBox = PieceProvider.getBlackKnight(movedPiece.position)
        c3.pieceOnBox = PieceProvider.getBlackRook(movedPiece.position)
        c4.pieceOnBox = PieceProvider.getBlackBishop(movedPiece.position)

        Globals.pawnPromoted = movedPiece
    }
}

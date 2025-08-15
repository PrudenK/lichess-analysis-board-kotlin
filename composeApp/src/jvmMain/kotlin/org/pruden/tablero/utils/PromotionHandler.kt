package org.pruden.tablero.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object PromotionHandler {

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

    fun handlePromotionClick(clickedRow: Int) {
        if (Globals.isWhitePromotion.value) {
            applyPromotionChoiceWhite(clickedRow)
        } else if (Globals.isBlackPromotion.value) {
            applyPromotionChoiceBlack(clickedRow)
        }
    }

    private fun applyPromotionChoiceWhite(clickedRow: Int) {
        val promotionCol = Globals.promotionCol
        val pawnPromoted = Globals.pawnPromoted!!

        val piecePromoted = when (clickedRow) {
            1 -> PieceProvider.getWhiteKnight(pawnPromoted)
            2 -> PieceProvider.getWhiteRook(pawnPromoted)
            3 -> PieceProvider.getWhiteBishop(pawnPromoted)
            else -> PieceProvider.getWhiteQueen(pawnPromoted)
        }

        Globals.chessBoard[0][promotionCol].pieceOnBox = piecePromoted
        Globals.chessBoard[1][promotionCol].pieceOnBox = Globals.promotionBuffer[0]
        Globals.chessBoard[2][promotionCol].pieceOnBox = Globals.promotionBuffer[1]
        Globals.chessBoard[3][promotionCol].pieceOnBox = Globals.promotionBuffer[2]
        Globals.promotionBuffer.clear()

        CellHandler.setCellsDisable(false)
        Globals.isWhitePromotion.value = false
    }

    private fun applyPromotionChoiceBlack(clickedRow: Int) {
        val promotionCol = Globals.promotionCol
        val pawnPromoted = Globals.pawnPromoted!!

        val piecePromoted = when (clickedRow) {
            6 -> PieceProvider.getBlackKnight(pawnPromoted)
            5 -> PieceProvider.getBlackRook(pawnPromoted)
            4 -> PieceProvider.getBlackBishop(pawnPromoted)
            else -> PieceProvider.getBlackQueen(pawnPromoted)
        }

        Globals.chessBoard[7][promotionCol].pieceOnBox = piecePromoted
        Globals.chessBoard[6][promotionCol].pieceOnBox = Globals.promotionBuffer[0]
        Globals.chessBoard[5][promotionCol].pieceOnBox = Globals.promotionBuffer[1]
        Globals.chessBoard[4][promotionCol].pieceOnBox = Globals.promotionBuffer[2]
        Globals.promotionBuffer.clear()

        CellHandler.setCellsDisable(false)
        Globals.isBlackPromotion.value = false
    }

    fun preparePromotionSelectionWhite(movedPiece: Piece) {
        CellHandler.setCellsDisable(true)

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

        c1.pieceOnBox = PieceProvider.getWhiteQueen(movedPiece)
        c2.pieceOnBox = PieceProvider.getWhiteKnight(movedPiece)
        c3.pieceOnBox = PieceProvider.getWhiteRook(movedPiece)
        c4.pieceOnBox = PieceProvider.getWhiteBishop(movedPiece)

        Globals.pawnPromoted = movedPiece
    }

    fun preparePromotionSelectionBlack(movedPiece: Piece) {
        CellHandler.setCellsDisable(true)

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

        c1.pieceOnBox = PieceProvider.getBlackQueen(movedPiece)
        c2.pieceOnBox = PieceProvider.getBlackKnight(movedPiece)
        c3.pieceOnBox = PieceProvider.getBlackRook(movedPiece)
        c4.pieceOnBox = PieceProvider.getBlackBishop(movedPiece)

        Globals.pawnPromoted = movedPiece
    }
}

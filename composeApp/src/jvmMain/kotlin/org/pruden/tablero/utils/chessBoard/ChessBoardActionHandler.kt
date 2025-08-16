package org.pruden.tablero.utils.chessBoard

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.LastMove
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import org.pruden.tablero.utils.moves.MoveCalculator
import org.pruden.tablero.utils.promotion.PromotionHandler
import kotlin.math.abs

object ChessBoardActionHandler {
    fun removePieceSelection(){
        Globals.possibleMoves.value = emptyList()
        Globals.chessBoard.forEach { row ->
            row.forEach { box ->
                box.pieceOnBox?.isSelected?.value = false
            }
        }
    }

    fun movePiece(
        clickedRow: Int,
        clickedCol: Int,
        selRow: Int,
        selCol: Int,
    ): Piece{
        Globals.chessBoard[clickedRow][clickedCol].pieceOnBox = Globals.chessBoard[selRow][selCol].pieceOnBox
        Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.position = Pair(clickedCol, clickedRow)
        Globals.chessBoard[selRow][selCol].pieceOnBox = null
        return Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!
    }

    fun saveLastMove(
        clickedRow: Int,
        clickedCol: Int,
        selRow: Int,
        selCol: Int,
    ){
        Globals.lastPieceStartPos = Globals.chessBoard[selRow][selCol].pieceOnBox!!.position
        Globals.lastMove.value = LastMove(
            from = Pair(selCol, selRow),
            to = Pair(clickedCol, clickedRow)
        )
    }

    fun getSelectedPiecePos(): Pair<Int, Int>? {
        return Globals.chessBoard
            .flatMapIndexed { rowIndex, row ->
                row.mapIndexedNotNull { colIndex, box ->
                    if (box.pieceOnBox?.isSelected?.value == true) Pair(colIndex, rowIndex) else null
                }
            }.firstOrNull()
    }

    fun makePromotionOrCompleteMove(
        movedPiece: Piece,
        clickedRow: Int,
        clickedCol: Int,
        selRow: Int,
        selCol: Int,
    ){
        PromotionHandler.verifyPromotion(movedPiece)

        if (Globals.isWhitePromotion.value) {
            PromotionHandler.preparePromotionSelectionWhite(movedPiece)
        } else if (Globals.isBlackPromotion.value) {
            PromotionHandler.preparePromotionSelectionBlack(movedPiece)
        }else{
            Globals.lastMove.value = LastMove(
                from = Pair(selCol, selRow),
                to = Pair(clickedCol, clickedRow)
            )
            Globals.isWhiteMove.value = !Globals.isWhiteMove.value
        }
    }

    fun tryToSelectAPiece(
        clickedRow: Int,
        clickedCol: Int,
    ){
        if (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox != null) {
            if(
                (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.color == Color.White && Globals.isWhiteMove.value)
                || (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.color == Color.Black && !Globals.isWhiteMove.value)
            ) {
                Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.isSelected.value = true
                Globals.possibleMoves.value = MoveCalculator.getPosibleMoves(
                    Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!
                )
            }
        }
    }

    fun verifyIfCheck(){
        if (Globals.isWhiteMove.value) {
            val attacked = MoveCalculator.calculateCellsControledByOponent(Color.White)
            val wKing = CellHandler.getKingByColor(Color.White)
            val inCheck = attacked.contains(wKing.position)
            Globals.whiteIsChecked.value = inCheck
            Globals.checkedKingPos.value = if (inCheck) wKing.position else null
        } else {
            val attacked = MoveCalculator.calculateCellsControledByOponent(Color.Black)
            val bKing = CellHandler.getKingByColor(Color.Black)
            val inCheck = attacked.contains(bKing.position)
            Globals.blackIsChecked.value = inCheck
            Globals.checkedKingPos.value = if (inCheck) bKing.position else null
        }
    }

    fun enPassantCalculations(
        movedPiece: Piece,
        clickedRow: Int,
        clickedCol: Int,
        selRow: Int,
        selCol: Int,
    ){
        if(Globals.posiblePassant){
            if(Pair(clickedCol, clickedRow) == Globals.enPassantCell) {
                val offset = if(Globals.isWhiteMove.value) -1 else 1

                val (row, col) = Globals.enPassantCell
                Globals.chessBoard[col - offset][row].pieceOnBox = null
                Globals.refreshBoard.value = !Globals.refreshBoard.value
            }
        }

        Globals.posiblePassant = false

        if(movedPiece.type == PieceType.Pawn){
            if(abs(selRow - clickedRow) == 2){
                Globals.posiblePassant = true
                Globals.colPassant = selCol
            }
        }
    }
}
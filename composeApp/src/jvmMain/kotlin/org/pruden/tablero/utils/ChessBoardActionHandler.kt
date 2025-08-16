package org.pruden.tablero.utils

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.LastMove
import org.pruden.tablero.models.Piece

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
                (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.color == org.pruden.tablero.models.Color.White && Globals.isWhiteMove.value)
                || (Globals.chessBoard[clickedRow][clickedCol].pieceOnBox!!.color == org.pruden.tablero.models.Color.Black && !Globals.isWhiteMove.value)
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
            val attacked = MoveCalculator.calculateCellsControledByOponent(org.pruden.tablero.models.Color.White)
            val wKing = CellHandler.getKingByColor(org.pruden.tablero.models.Color.White)
            val inCheck = attacked.contains(wKing.position)
            Globals.whiteIsChecked.value = inCheck
            Globals.checkedKingPos.value = if (inCheck) wKing.position else null
        } else {
            val attacked = MoveCalculator.calculateCellsControledByOponent(org.pruden.tablero.models.Color.Black)
            val bKing = CellHandler.getKingByColor(org.pruden.tablero.models.Color.Black)
            val inCheck = attacked.contains(bKing.position)
            Globals.blackIsChecked.value = inCheck
            Globals.checkedKingPos.value = if (inCheck) bKing.position else null
        }
    }
}
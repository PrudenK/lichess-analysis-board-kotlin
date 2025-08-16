package org.pruden.tablero.utils.castle

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Color
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object CastleHandler {
    fun moveRookOnCastle(
        movedPiece: Piece,
        selRow: Int,
        selCol: Int,
        clickedRow: Int,
        clickedCol: Int
    ){
        if(movedPiece.type == PieceType.King){
            val startPos = Globals.chessBoard[selRow][selCol].boxNotation
            val endPos = Globals.chessBoard[clickedRow][clickedCol].boxNotation
            if(movedPiece.color == Color.White) {
                if(startPos == "e1" && endPos == "g1") {
                    val rook = Globals.chessBoard[7][7].pieceOnBox!!
                    Globals.chessBoard[7][7].pieceOnBox = null
                    Globals.chessBoard[7][5].pieceOnBox = rook
                    rook.position = Pair(5,7)
                }else if(startPos == "e1" && endPos == "c1") {
                    val rook = Globals.chessBoard[7][0].pieceOnBox!!
                    Globals.chessBoard[7][0].pieceOnBox = null
                    Globals.chessBoard[7][3].pieceOnBox = rook
                    rook.position = Pair(3,7)
                }
            }else{
                if(startPos == "e8" && endPos == "g8") {
                    val rook = Globals.chessBoard[0][7].pieceOnBox!!
                    Globals.chessBoard[0][7].pieceOnBox = null
                    Globals.chessBoard[0][5].pieceOnBox = rook
                    rook.position = Pair(5,0)
                }else if(startPos == "e8" && endPos == "c8") {
                    val rook = Globals.chessBoard[0][0].pieceOnBox!!
                    Globals.chessBoard[0][0].pieceOnBox = null
                    Globals.chessBoard[0][3].pieceOnBox = rook
                    rook.position = Pair(3,0)
                }
            }
        }
    }

    fun disableCastleIfKingOrRookMoved(
        movedPiece: Piece,
    ){
        if(movedPiece.color == Color.White) {
            if(movedPiece.type == PieceType.King) {
                Globals.whiteCastle = Triple(false, true, false)
            }else if(movedPiece.type == PieceType.Rook) {

                Globals.whiteCastle = Triple(movedPiece.id == 24, Globals.whiteCastle.second, movedPiece.id == 31)
            }
        }else{
            if(movedPiece.type == PieceType.King) {
                Globals.blackCastle = Triple(false, true, false)
            }else if(movedPiece.type == PieceType.Rook) {
                Globals.blackCastle = Triple(movedPiece.id == 0, Globals.blackCastle.second, movedPiece.id == 7)
            }
        }
    }

    fun addShortCastleMoveIfAvailable(
        piece: Piece,
        cellsControledByOtherPieces: MutableSet<Pair<Int, Int>>,
        miKingMoves: MutableList<Pair<Int, Int>>,
    ){
        if(piece.color == Color.White){
            if(!Globals.whiteCastle.second){
                if(!Globals.whiteCastle.third){
                    if(Globals.chessBoard[7][5].isFreeCell() &&
                        Globals.chessBoard[7][6].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(5, 7))
                    )
                        miKingMoves += Pair(6, 7)
                }
            }
        }else{
            if(!Globals.blackCastle.second){
                if(!Globals.blackCastle.third){
                    if(Globals.chessBoard[0][5].isFreeCell() &&
                        Globals.chessBoard[0][6].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(5, 0))

                    )
                        miKingMoves += Pair(6, 0)
                }
            }
        }
    }

    fun addLongCastleMoveIfAvailable(
        piece: Piece,
        cellsControledByOtherPieces: MutableSet<Pair<Int, Int>>,
        miKingMoves: MutableList<Pair<Int, Int>>,
    ){
        if(piece.color == Color.White){
            if(!Globals.whiteCastle.second){
                if(!Globals.whiteCastle.first){
                    if(Globals.chessBoard[7][3].isFreeCell() &&
                        Globals.chessBoard[7][2].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(2, 7))
                    )
                        miKingMoves += Pair(2, 7)
                }
            }
        }else{
            if(!Globals.blackCastle.second){
                if(!Globals.blackCastle.first){
                    if(Globals.chessBoard[0][3].isFreeCell() &&
                        Globals.chessBoard[0][2].isFreeCell() &&
                        !cellsControledByOtherPieces.contains(Pair(2, 0))

                    )
                        miKingMoves += Pair(2, 0)
                }
            }
        }
    }
}
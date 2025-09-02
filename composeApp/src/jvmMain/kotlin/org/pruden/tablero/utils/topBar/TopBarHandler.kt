package org.pruden.tablero.utils.topBar

import androidx.compose.ui.geometry.Offset
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.Color
import org.pruden.tablero.utils.chessBoard.loadChessBoard

object TopBarHandler {
    fun restartBoard() {
        Globals.isDarkMode.value = false

        Globals.chessBoard = Array(Globals.BOX_HEIGHT) { Array(Globals.BOX_WIDTH) { BoxModel() } }
        Globals.possibleMoves.value = emptyList()

        Globals.isWhiteMove.value = true
        Globals.isBoardLoaded.value = false

        Globals.whiteCastle = Triple(false, false, false)
        Globals.blackCastle = Triple(false, false, false)

        Globals.promotionCol = 0
        Globals.isWhitePromotion.value = false
        Globals.isBlackPromotion.value = false

        Globals.promotionBuffer.clear()
        Globals.pawnPromoted = null
        Globals.lastPieceStartPos = Pair(-1, -1)

        Globals.refreshBoard.value = false

        Globals.lastMove.value = null

        Globals.checkedKingPos.value = null
        Globals.whiteIsChecked.value = false
        Globals.blackIsChecked.value = false
        Globals.posiblePassant = false
        Globals.colPassant = -1
        Globals.enPassantCell = Pair(-1, -1)

        Globals.result.value = -1
        Globals.isGameOver.value = false

        Globals.movesNodesBuffer.value = mutableListOf()

        Globals.halfMoves = 0

        Globals.fenEnPassant = "-"

        Globals.isDragging.value = false
        Globals.dragPointerPx.value = Offset.Zero
        Globals.dragPng.value = null

        loadChessBoard()

        Globals.refreshBoard.value = !Globals.refreshBoard.value
        Globals.isBoardLoaded.value = true

        printBoard()
    }

    fun printBoard(board: Array<Array<BoxModel>> = Globals.chessBoard) {
        println("_______________________________________________________________________________________________________")

        for (i in 0..7) {
            for (j in 0..7) {
                val piece = board[i][j].pieceOnBox
                val text = if (piece != null) {
                    val color = if (piece.color == Color.White) "W" else "B"
                    "${piece.type}($color)"
                } else {
                    " . "
                }
                print(text.padEnd(12))
            }
            println()
        }
    }
}
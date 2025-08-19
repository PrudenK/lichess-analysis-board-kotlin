package org.pruden.tablero.globals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.LastMove
import org.pruden.tablero.models.Piece

object Globals {

    val knightDirections = listOf(
        Pair(-2, -1),
        Pair(-1, -2),
        Pair(1, -2),
        Pair(-2, 1),
        Pair(2, -1),
        Pair(2, 1),
        Pair(1, 2),
        Pair(-1, 2)
    )

    val rookDirections = listOf(
        Pair(1, 0),
        Pair(-1, 0),
        Pair(0, 1),
        Pair(0, -1)
    )

    val bishopDirections = listOf(
        Pair(1, 1),
        Pair(-1, -1),
        Pair(-1, 1),
        Pair(1, -1)
    )


    val isDarkMode = mutableStateOf(false)

    val WhiteBox = Color(0xFFF0D9B5)
    val BlackBox = Color(0xFFB58863)


    const val BOX_SIZE = 80
    const val BOX_WIDTH = 8
    const val BOX_HEIGHT = 8

    var chessBoard = Array(BOX_HEIGHT) { Array(BOX_WIDTH) { BoxModel() } }
    val possibleMoves = mutableStateOf<List<Pair<Int, Int>>>(emptyList())

    val isWhiteMove = mutableStateOf(true)
    val isBoardLoaded = mutableStateOf(false)

    var whiteCastle = Triple(false, false, false) //White Rook King Rook
    var blackCastle = Triple(false, false, false) //Black Rook King Rook

    var promotionCol = 0
    val isWhitePromotion = mutableStateOf(false)
    val isBlackPromotion = mutableStateOf(false)

    val promotionBuffer = mutableListOf<Piece?>()

    var pawnPromoted: Piece? = null
    var lastPieceStartPos = Pair(-1, -1)

    val refreshBoard = mutableStateOf(false)

    val lastMove = mutableStateOf<LastMove?>(null)

    val checkedKingPos = mutableStateOf<Pair<Int,Int>?>(null)
    val whiteIsChecked = mutableStateOf(false)
    val blackIsChecked = mutableStateOf(false)
    var posiblePassant = false
    var colPassant = -1
    var enPassantCell = Pair(-1, -1)

    val result = mutableStateOf<Int>(-1) // 0(wWin), 1(bWin), 2(draw, stalemate), 3(draw, death position)

    val isGameOver = mutableStateOf(false)

    val movesBuffer = mutableStateOf(mutableListOf<String>())

    var halfMoves = 0

    val refreshMovesPanel = mutableStateOf(false)
}
package org.pruden.tablero.globals

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.pruden.tablero.models.*

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


    val boardHeightDp = mutableStateOf(0.dp)
    val boardWidthDp = mutableStateOf(0.dp)

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

    val result = mutableStateOf(-1) // 0(wWin), 1(bWin), 2(draw, stalemate), 3(draw, death position)

    val isGameOver = mutableStateOf(false)

    val movesBufferNotation = mutableStateOf(mutableListOf<NotationMove>())


    var halfMoves = 0

    val refreshMovesPanel = mutableStateOf(false)

    var fenEnPassant = "-"

    val initialFenPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

    val isModuleActivated = mutableStateOf(true)
    val valoration = mutableStateOf(20)

    val leftSideContentWidth = 370.dp
    val isBoardRotated = mutableStateOf(false)

    val isDragging = mutableStateOf(false)
    val dragPointerPx = mutableStateOf(Offset.Zero)
    val dragPng = mutableStateOf<DrawableResource?>(null)
    val cellSizePx = mutableStateOf(0f)


    val bestMove = mutableStateOf<BestMove?>(null)
}
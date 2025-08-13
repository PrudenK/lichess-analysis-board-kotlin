package org.pruden.tablero.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.BoxModel
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.bB
import tableroajedrez.composeapp.generated.resources.bK
import tableroajedrez.composeapp.generated.resources.bN
import tableroajedrez.composeapp.generated.resources.bP
import tableroajedrez.composeapp.generated.resources.bQ
import tableroajedrez.composeapp.generated.resources.bR
import tableroajedrez.composeapp.generated.resources.pieces
import tableroajedrez.composeapp.generated.resources.wB
import tableroajedrez.composeapp.generated.resources.wK
import tableroajedrez.composeapp.generated.resources.wN
import tableroajedrez.composeapp.generated.resources.wP
import tableroajedrez.composeapp.generated.resources.wQ
import tableroajedrez.composeapp.generated.resources.wR

@Composable
fun ChessBoard(
    modifier: Modifier,
) {
    loadChessBoard()

    Box(
        modifier = modifier.padding(50.dp).background(Color.Black.copy(alpha = 0.6f)),
    ){
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp).background(Color.White),
        ){
            PaintChessBoardBoxes()

        }
    }
}

@Composable
fun PaintChessBoardBoxes() {
    Column(
        modifier = Modifier.padding(30.dp).border(4.dp, Color.Black).padding(4.dp),
    ) {
        repeat(Globals.BOX_HEIGHT) { y ->
            Row {
                repeat(Globals.BOX_WIDTH) { x ->
                    Cell(x, y, Globals.chessBoard[x][y]) { cx, cy ->


                        println("click ${Globals.chessBoard[cx][cy].boxNotation}")


                    }
                }
            }
        }
    }
}

@Composable
fun Cell(x: Int, y: Int, cell: BoxModel, onClick: (Int, Int) -> Unit) {
    Box(
        Modifier
            .size(Globals.BOX_SIZE.dp)
            .background(cell.color!!)
            .border(0.5.dp, Color.Black.copy(alpha = 0.5f))
            .clickable { onClick(x, y) }
    ) {
        cell.pieceOnBox?.let {
            Image(
                painter = painterResource(resource = it.png!!),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(6.dp))
        }
    }
}

fun loadChessBoard() {
    repeat(Globals.BOX_HEIGHT) { y ->
        repeat(Globals.BOX_WIDTH) { x ->
            val notation = "${'a' + x}${8 - y}"

            Globals.chessBoard[x][y] = BoxModel(
                boxNotation = notation,
                color = if ((x + y) % 2 == 0) Globals.WhiteBox else Globals.BlackBox,
                pieceOnBox = if(y >= 2 && y <= 5 ) null else {
                    val isBlackPiece = y < 2

                    val color = when{
                        isBlackPiece -> org.pruden.tablero.models.Color.Black
                        else -> org.pruden.tablero.models.Color.White
                    }

                    val typePiece = when{
                        y == 1 || y == 6 -> PieceType.Pawn
                        x == 0 || x == 7 -> PieceType.Rook
                        x == 1 || x == 6 -> PieceType.Knight
                        x == 2 || x == 5 -> PieceType.Bishop
                        x == 3 -> PieceType.Queen
                        else -> PieceType.King
                    }

                    val png = when (typePiece) {
                        PieceType.Pawn   -> if (isBlackPiece) Res.drawable.bP else Res.drawable.wP
                        PieceType.Rook   -> if (isBlackPiece) Res.drawable.bR else Res.drawable.wR
                        PieceType.Knight -> if (isBlackPiece) Res.drawable.bN else Res.drawable.wN
                        PieceType.Bishop -> if (isBlackPiece) Res.drawable.bB else Res.drawable.wB
                        PieceType.Queen  -> if (isBlackPiece) Res.drawable.bQ else Res.drawable.wQ
                        else   -> if (isBlackPiece) Res.drawable.bK else Res.drawable.wK
                    }

                    Piece(
                        id = if(isBlackPiece) x + y*8 else (x + y*8) - 32,
                        type = typePiece,
                        color = color,
                        png = png
                    )
                }
            )
        }
    }
}
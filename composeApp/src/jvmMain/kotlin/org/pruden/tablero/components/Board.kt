package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.loadChessBoard


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


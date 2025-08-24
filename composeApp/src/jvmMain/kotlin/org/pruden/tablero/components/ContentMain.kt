package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.components.bottomContent.FenBox
import org.pruden.tablero.components.bottomContent.PgnBox
import org.pruden.tablero.components.sideContent.RightSideContent
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.chessBoard.loadChessBoard


@Composable
fun ContentMain(
    modifier: Modifier,
) {
    val backgroundColor = Color(0xFF161512)

    if (!Globals.isBoardLoaded.value) {
        loadChessBoard()
        Globals.isBoardLoaded.value = true
    }

    Box(
        modifier = modifier.background(Color.Black.copy(alpha = 0.6f)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {

                    val gap = 12.dp
                    ChessBoard(modifier = Modifier.align(Alignment.Center))
                    RightSideContent(
                        modifier = Modifier
                            .height(Globals.boardHeightDp.value)
                            .align(Alignment.CenterStart)
                            .absoluteOffset(x = this.maxWidth / 2 + Globals.boardWidthDp.value / 2 + gap)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FenBox()
                    PgnBox()
                }
            }
        }
    }
}

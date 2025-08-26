package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.components.bottomContent.FenBox
import org.pruden.tablero.components.bottomContent.PgnBox
import org.pruden.tablero.components.leftSideContent.LeftSideContent
import org.pruden.tablero.components.rightSideContent.RightSideContent
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.chessBoard.loadChessBoard


@Composable
fun ContentMain(
    modifier: Modifier,
) {
    if (!Globals.isBoardLoaded.value) {
        loadChessBoard()
        Globals.isBoardLoaded.value = true
    }

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.backgroundGeneral),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val gap = 12.dp

                    LeftSideContent(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .absoluteOffset(x = this.maxWidth / 2 - Globals.boardWidthDp.value / 2 - gap - Globals.leftSideContentWidth)
                    )

                    ChessBoard(modifier = Modifier
                        .align(Alignment.Center)
                        //.rotate(180f)
                    )

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

package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.components.sideContent.SideContent
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
            .padding(50.dp)
            .background(Color.Black.copy(alpha = 0.6f)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.White),
        ) {
            Row {
                ChessBoard()
                SideContent(modifier = Modifier.height(Globals.boardHeightDp.value))
            }
        }
    }
}

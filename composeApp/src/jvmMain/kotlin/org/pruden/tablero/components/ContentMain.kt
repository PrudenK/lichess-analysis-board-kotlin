package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pruden.tablero.components.bottomContent.FenBox
import org.pruden.tablero.components.bottomContent.PgnBox
import org.pruden.tablero.components.sideContent.MovesPanel
import org.pruden.tablero.components.sideContent.SideContent
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
            Column {
                Row {
                    ChessBoard()
                    SideContent(modifier = Modifier.height(Globals.boardHeightDp.value))
                }
                FenBox()
                PgnBox()
            }
        }
    }
}

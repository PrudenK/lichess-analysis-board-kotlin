package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Juegan ${if (Globals.isWhiteMove.value) "Blancas" else "Negras"}",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 24.dp),
                    )
                }
            }
        }
    }
}

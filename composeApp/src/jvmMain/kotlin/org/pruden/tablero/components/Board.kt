package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import org.pruden.tablero.globals.Globals


@Composable
fun ChessBoard(
    modifier: Modifier,
) {

    Box(
        modifier = modifier.padding(50.dp).background(Color.Black.copy(alpha = 0.6f)),
    ){
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp).background(Color.White),
        ){
            ChessBoardBoxes()

        }


    }
}

@Composable
fun ChessBoardBoxes() {
    Column(
        modifier = Modifier.padding(30.dp).border(4.dp, Color.Black).padding(4.dp),
    ) {
        repeat(Globals.BOX_HEIGHT) { y ->
            Row {
                repeat(Globals.BOX_WIDTH) { x ->
                    Box(
                        Modifier
                            .size(Globals.BOX_SIZE.dp)
                            .background(if ((x + y) % 2 == 0) Globals.WhiteBox else Globals.BlackBox).border(0.1.dp, Color.Black.copy(alpha = 0.5f))
                    )
                }
            }
        }
    }
}

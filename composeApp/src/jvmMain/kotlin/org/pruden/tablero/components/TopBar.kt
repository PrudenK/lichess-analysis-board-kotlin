package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.moves.MoveCalculator
import org.pruden.tablero.utils.nodes.PGNHandler
import org.pruden.tablero.utils.topBar.TopBarHandler
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.bP
import tableroajedrez.composeapp.generated.resources.github
import java.awt.Desktop
import java.net.URI

@Composable
fun TopBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(56.dp)
            .background(Colors.backgroundGeneral).padding(top = 10.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextButton(
            onClick = {
                Desktop.getDesktop().browse(URI("https://github.com/PrudenK"))
            },
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PrudenK",
                    fontSize = 24.sp,
                    color = Colors.textColor
                )

                Spacer(Modifier.width(16.dp))

                Icon(
                    painter = painterResource(resource = Res.drawable.github),
                    contentDescription = null,
                    tint = Colors.textColor
                )
            }
        }

        TextButton(
            onClick = {
                TopBarHandler.restartBoard()
            },
        ){
            Text(
                text = "Reiniciar tablero",
                color = Colors.textColor,
                fontSize = 16.sp
            )
        }

        TextButton(
            onClick = {
                Globals.isBoardRotated.value = !Globals.isBoardRotated.value
            },
        ){
            Text(
                text = "Girar tablero",
                color = Colors.textColor,
                fontSize = 16.sp
            )
        }
    }
}
fun positionToChessNotation(position: Pair<Int, Int>): String {
    val l = "abcdefgh"
    return l[position.first].toString() + (7 - position.second +1)
}

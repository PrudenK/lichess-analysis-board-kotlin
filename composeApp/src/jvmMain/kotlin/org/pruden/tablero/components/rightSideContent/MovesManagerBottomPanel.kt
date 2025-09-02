package org.pruden.tablero.components.rightSideContent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.utils.moves.MovesManager
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.moves_all
import tableroajedrez.composeapp.generated.resources.moves_one

@Composable
fun MovesManagerBottomPanel(){
    Box {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = {
                    MovesManager.goToStart()
                }
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.moves_all),
                    contentDescription = null,
                    tint = Colors.movesColor,
                    modifier = Modifier.size(24.dp).rotate(180f)
                )
            }

            IconButton(
                onClick = {
                    MovesManager.stepBack()
                }
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.moves_one),
                    contentDescription = null,
                    tint = Colors.movesColor,
                    modifier = Modifier.size(24.dp).rotate(180f)
                )
            }

            IconButton(
                onClick = {
                    MovesManager.stepForward()
                }
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.moves_one),
                    contentDescription = null,
                    tint = Colors.movesColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(
                onClick = {
                    MovesManager.goToEnd()
                }
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.moves_all),
                    contentDescription = null,
                    tint = Colors.movesColor,
                    modifier = Modifier.size(24.dp)
                )
            }

        }
    }
}
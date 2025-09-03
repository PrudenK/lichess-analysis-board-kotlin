package org.pruden.tablero.components.rightSideContent.variants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.pruden.tablero.components.rightSideContent.movesTexts.MainLineText
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.moves.MovesManager
import org.pruden.tablero.utils.notation.variant.VariantHelper

@Composable
fun MainLineRowComponent(
    steps: Int,
    white: MoveNode?,
    black: MoveNode?
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "$steps",
            fontSize = 18.sp,
            lineHeight = 28.sp,
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
                .background(Colors.secondary)
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Center,
            color = Colors.movesColor.copy(alpha = 0.5f)
        )

        VerticalDivider(
            color = Colors.movesColor.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxHeight()
        )

        val parentOfWhite = VariantHelper.getParentOfWhite(white)

        val hasWhiteSiblings = VariantHelper.getIfhasWhiteSiblings(white, parentOfWhite)

        MainLineText(
            text = white?.san.orEmpty(),
            modifier = Modifier.weight(1f),
            textColor = Colors.movesColor,
            hoverColor = Colors.hoverColor,
            padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            isThisMove = white?.isActualMove == true,
            onClick = {
                if(white != null){
                    MovesManager.goToClickedMove(white)
                }
            }
        )

        MainLineText(
            text = when {
                hasWhiteSiblings -> "..."
                black != null -> black.san.orEmpty()
                else -> ""
            },
            modifier = Modifier.weight(1f),
            textColor = Colors.movesColor,
            hoverColor = Colors.hoverColor,
            padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            isThisMove = when{
                hasWhiteSiblings -> false
                else -> black?.isActualMove == true
            },
            onClick = {
                if(black != null){
                    MovesManager.goToClickedMove(black)
                }
            }
        )
    }
}

@Composable
fun MainLineRowComponentForCase1(
    steps: Int,
    black: MoveNode
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {

        Text(
            text = "$steps",
            fontSize = 18.sp,
            lineHeight = 28.sp,
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
                .background(Colors.secondary)
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Center,
            color = Colors.movesColor.copy(alpha = 0.5f)
        )

        VerticalDivider(
            color = Colors.movesColor.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxHeight()
        )

        MainLineText(
            text = "...",
            modifier = Modifier.weight(1f),
            textColor = Colors.movesColor,
            hoverColor = Colors.hoverColor,
            padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            isThisMove = false,
            onClick = {

            }
        )

        MainLineText(
            text = black.san.orEmpty(),
            modifier = Modifier.weight(1f),
            textColor = Colors.movesColor,
            hoverColor = Colors.hoverColor,
            padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            isThisMove = black.isActualMove,
            onClick = {
                MovesManager.goToClickedMove(black)
            }
        )
    }
}
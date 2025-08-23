package org.pruden.tablero.components.sideContent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.result.ResultHandler

@Composable
fun SideContent(
    modifier: Modifier
){
    Column {
        Row {
            ModuleBar(modifier = Modifier.width(15.dp).height(Globals.boardHeightDp.value).padding(top = 10.dp))

            MovesPanel(modifier = modifier)

            Column(
                modifier = modifier
                    .padding(12.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Juegan ${if (Globals.isWhiteMove.value) "Blancas" else "Negras"}",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 24.dp),
                    )
                }

                Row {
                    Text(
                        text = "Resultado: ${ResultHandler.getResultToString()}",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 12.dp),
                    )

                }
            }

        }
    }
}
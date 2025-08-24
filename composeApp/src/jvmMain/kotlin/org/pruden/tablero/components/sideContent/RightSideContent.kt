package org.pruden.tablero.components.sideContent

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pruden.tablero.globals.Globals

@Composable
fun RightSideContent(
    modifier: Modifier
){
    Column {
        Row {
            ModuleBar(modifier = modifier.width(15.dp).height(Globals.boardHeightDp.value).padding(top = 10.dp))
            MovesPanel(modifier = modifier)
        }
    }
}
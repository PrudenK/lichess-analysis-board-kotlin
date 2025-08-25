package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pruden.tablero.globals.Globals

@Composable
fun LeftSideContent(
    modifier: Modifier
){
    Column(
        modifier = modifier.height(Globals.boardHeightDp.value - 10.dp)
    ) {
        GameSelector()

        Spacer(modifier.height(24.dp))

        WikiBook()
    }



}
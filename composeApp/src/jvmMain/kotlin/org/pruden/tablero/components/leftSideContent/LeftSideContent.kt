package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LeftSideContent(
    modifier: Modifier
){
    Column(
        modifier = modifier
    ) {
        GameSelector()
    }



}
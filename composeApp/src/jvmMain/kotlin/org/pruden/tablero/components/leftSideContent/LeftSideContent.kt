package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LeftSideContent(
    modifier: Modifier
){
    Column(
        modifier = modifier
    ) {
        GameSelector()

        Spacer(modifier.height(24.dp))

        WikiBook()
    }



}
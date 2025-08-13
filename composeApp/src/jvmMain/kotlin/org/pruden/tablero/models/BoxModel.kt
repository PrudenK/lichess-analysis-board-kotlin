package org.pruden.tablero.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class BoxModel(
    val boxNotation: String = "",
    val color: androidx.compose.ui.graphics.Color? = null,
    val pieceOnBox: Piece? = null
){

}

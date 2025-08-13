package org.pruden.tablero.globals

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import tableroajedrez.composeapp.generated.resources.Res

object Globals {
    val isDarkMode = mutableStateOf(false)

    val WhiteBox = Color(0xFFF0D9B5)
    val BlackBox = Color(0xFFB58863)


    const val BOX_SIZE = 80
    const val BOX_WIDTH = 8
    const val BOX_HEIGHT = 8

    val board = Array(BOX_HEIGHT) { IntArray(BOX_WIDTH) { 0 } }
}
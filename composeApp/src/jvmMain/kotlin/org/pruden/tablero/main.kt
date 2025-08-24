package org.pruden.tablero

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TableroAjedrez",
        state = rememberWindowState(width = 1700.dp, height = 1000.dp),
        resizable = false
    ) {
        App()
    }
}

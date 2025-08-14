package org.pruden.tablero

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.pruden.tablero.components.ChessBoard
import org.pruden.tablero.components.TopBar
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.ui.DarkColorScheme
import org.pruden.tablero.ui.LightColorScheme
@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if(Globals.isDarkMode.value) DarkColorScheme else LightColorScheme
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBar()
            ChessBoard(modifier = Modifier.weight(1f).fillMaxSize())
        }

    }
}
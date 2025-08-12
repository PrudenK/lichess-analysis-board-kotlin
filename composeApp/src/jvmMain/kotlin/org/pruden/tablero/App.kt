package org.pruden.tablero

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
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
        TopBar()
    }
}
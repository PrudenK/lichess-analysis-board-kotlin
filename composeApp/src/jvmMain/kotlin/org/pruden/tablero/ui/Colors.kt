package org.pruden.tablero.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import org.pruden.tablero.globals.Globals

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),       // Azul principal
    onPrimary = Color.White,           // Texto sobre el color primario
    secondary = Color(0xFF03A9F4),     // Azul claro
    onSecondary = Color.Black,         // Texto sobre el secundario
    tertiary = Color(0xFFFF9800),      // Naranja para acentos
    onTertiary = Color.Black,
    background = Color(0xFFF5F5F5),    // Fondo general claro
    onBackground = Color.Black,        // Texto sobre fondo
    surface = Color.White,             // Superficies como tarjetas
    onSurface = Color.Black,           // Texto sobre superficies
    error = Color(0xFFD32F2F),         // Rojo de error
    onError = Color.White              // Texto sobre error
)

val DarkColorScheme = darkColorScheme()
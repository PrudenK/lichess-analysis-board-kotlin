package org.pruden.tablero

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.skia.Surface
import org.pruden.tablero.components.TopBar
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.ui.DarkColorScheme
import org.pruden.tablero.ui.LightColorScheme

import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if(Globals.isDarkMode.value) DarkColorScheme else LightColorScheme
    ) {
        TopBar()
    }
}
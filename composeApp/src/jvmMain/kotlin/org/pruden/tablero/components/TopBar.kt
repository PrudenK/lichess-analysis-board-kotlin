package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextButton(
            onClick = {},
        ){
            Text("adsfa")
        }
        TextButton(
            onClick = {},
        ){
            Text("adsfa")
        }

        TextButton(
            onClick = {},
        ){
            Text("adsfa")
        }

        TextButton(
            onClick = {},
        ){
            Text("adsfa")
        }
    }
}
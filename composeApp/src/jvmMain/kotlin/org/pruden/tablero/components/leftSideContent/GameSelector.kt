package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Colors
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.caret_down
import tableroajedrez.composeapp.generated.resources.no_check
import tableroajedrez.composeapp.generated.resources.standard_gamemode

@Composable
fun GameSelector() {
    val boxWidth = 370.dp
    val backgroundColor = Color(0xFF373531)
    val caretColor = Color(0xFF3689d5)


    Box(
        modifier = Modifier
            .width(boxWidth)
            .background(backgroundColor, RoundedCornerShape(4.dp))
    ){
        Row (
            modifier = Modifier.padding(6.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ){
            Spacer(Modifier.width(16.dp))

            Icon(
                painter = painterResource(Res.drawable.standard_gamemode),
                contentDescription = null,
                tint = Colors.textColor,
                modifier = Modifier.size(28.dp)
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = "Standard",
                color = Colors.textColor,
                fontSize = 24.sp,
            )

            Spacer(Modifier.weight(1f))

            Icon(
                painter = painterResource(Res.drawable.caret_down),
                contentDescription = null,
                tint = caretColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(16.dp))


        }


    }

}
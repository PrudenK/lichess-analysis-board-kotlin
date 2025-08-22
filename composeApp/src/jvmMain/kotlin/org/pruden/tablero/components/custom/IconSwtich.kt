package org.pruden.tablero.components.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.check
import tableroajedrez.composeapp.generated.resources.no_check

@Composable
fun IconSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbSize = 24.dp
    val switchWidth = 45.dp
    val switchHeight = 28.dp

    val backgroundColor = if (checked) Color(0xFF629924) else Color(0xFF666665)
    val circleColor = if(checked) Color(0xFF35332f) else Color(0xFF35332f)
    val borderColor = Color(0xFF404040)

    val icon = if (checked) Res.drawable.check else Res.drawable.no_check



    Box(
        modifier = modifier
            .width(switchWidth)
            .height(switchHeight)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable { onCheckedChange(!checked) }
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(50)),
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Icon(
            painter = painterResource(resource = icon),
            contentDescription = null,
            modifier = Modifier
                .size(thumbSize)
                .background(circleColor, shape = CircleShape)
                .padding(if(checked) 6.dp else 7.dp),
            tint = backgroundColor
        )

    }
}

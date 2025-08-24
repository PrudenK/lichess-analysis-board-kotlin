package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.components.rightSideContent.MovesPanel
import org.pruden.tablero.globals.Colors
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.caret_down
import tableroajedrez.composeapp.generated.resources.wikibook_caret

@Composable
fun WikiBook() {
    val backgroundColor = Color(0xFF373531)

    var bookIsOpen by remember { mutableStateOf(true) }

    Box(modifier = Modifier.width(370.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .align(Alignment.TopStart)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-18).dp)
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .background(backgroundColor, RoundedCornerShape(6.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable {
                            bookIsOpen = !bookIsOpen
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "WikiBook",
                            color = Colors.textColor,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(6.dp)
                        )

                        Icon(
                            painter = painterResource(resource = Res.drawable.wikibook_caret),
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .rotate(if(bookIsOpen) 180f else 0f)
                                .padding(top = if(bookIsOpen) 0.dp else 4.dp, bottom = if(bookIsOpen) 4.dp else 0.dp),
                            tint = Colors.textColor
                        )
                    }
                }


                Spacer(Modifier.width(48.dp))
            }
        }
    }
}

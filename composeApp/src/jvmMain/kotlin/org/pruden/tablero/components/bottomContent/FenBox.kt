package org.pruden.tablero.components.bottomContent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals

@Composable
fun FenBox(){
    Box(
        modifier = Modifier
            .padding(top = 15.dp, start = 10.dp)
            .width(Globals.boardWidthDp.value)
    ){

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "FEN",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp, end = 14.dp),

            )

            Box(
                modifier = Modifier
                    .background(Colors.backgroundFenBox, RoundedCornerShape(4.dp))
                    .border(0.2.dp, Color.Gray.copy(0.4f), RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
                    .weight(1f)
            ){
                val fenText = Globals.movesBufferNotation.value.find { it.isActualMove }?.fen
                    ?: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

                BasicTextField(
                    value = fenText,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Colors.textColor, fontSize = 16.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.backgroundFenBox, RoundedCornerShape(4.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }
    }
}
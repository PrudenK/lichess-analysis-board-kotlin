package org.pruden.tablero.components.rightSideContent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.pruden.tablero.components.custom.IconSwitch
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.result.ResultHandler
import java.util.*

@Composable
fun ModuleToggle(){
    if (Globals.isModuleActivated.value) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Colors.moduleActivateColor, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
        )
    }

    Box(
        modifier = Modifier.padding(15.dp)
    ){
        Row {
            IconSwitch(
                checked = Globals.isModuleActivated.value,
                onCheckedChange = {
                    Globals.isModuleActivated.value = it
                },
                modifier = Modifier.scale(1.2f).padding(top = 4.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 22.dp)
                    .height(32.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (Globals.isModuleActivated.value) {
                    var text = (if (Globals.valoration.value > 0) "+" else "") + String.format(Locale.US, "%.1f", Globals.valoration.value / 100f)

                    if(Globals.movesToMate.value != null){
                        text = "#${Globals.movesToMate.value}"
                    }

                    /*
                    if(Globals.result.value != -1){
                        text = "-"
                    }
                     */

                    Text(
                        text = text,
                        fontSize = 24.sp,
                        color = Colors.textColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
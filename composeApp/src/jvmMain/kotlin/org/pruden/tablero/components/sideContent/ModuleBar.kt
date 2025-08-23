package org.pruden.tablero.components.sideContent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.pruden.tablero.api.constants.ApiChess
import org.pruden.tablero.api.objects.request.EvalRequest
import org.pruden.tablero.globals.Globals


@Composable
fun ModuleBar(
    modifier: Modifier = Modifier
) {
    val actualMove = Globals.movesBufferNotation.value.find { it.isActualMove }
    var evalCp by remember(actualMove?.fen) { mutableStateOf(20) }

    LaunchedEffect(actualMove?.fen) {
        if(Globals.isModuleActivated.value){
            val fen = actualMove?.fen ?: Globals.initialFenPos
            try {
                val res = withContext(Dispatchers.IO) {
                    ApiChess.moduleService.evaluatePosition(EvalRequest(fen = fen, depth = 18, variants = 1))
                }
                evalCp = ((res.eval ?: 0.0) * 100).toInt()
                Globals.valoration.value = evalCp
            } catch (e: Exception) {
                evalCp = 20
                Globals.valoration.value = evalCp
            }
        }
    }


    val evalClamped = evalCp.coerceIn(-1000, 1000)
    val percent = (evalClamped + 1000) / 2000f

    val whiteEvalColor = Color(0xFFa0a0a0)
    val blackEvalColor = Color(0xFF666666)
    val markerColor = Color(0xFFD08B5B)

    val innerH = Globals.boardHeightDp.value
    val splitH = innerH * percent
    val step = (innerH - 10.dp) / 8

    Box(
        modifier = modifier
            .width(15.dp)
            .height(innerH)
    ) {
        if(Globals.isModuleActivated.value){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(innerH - splitH)
                    .align(Alignment.TopStart)
                    .background(blackEvalColor)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(splitH)
                    .align(Alignment.BottomStart)
                    .background(whiteEvalColor)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .offset(y = (Globals.boardHeightDp.value / 2) - 6.dp)
                    .background(markerColor, RoundedCornerShape(2.dp))
            )


            for (i in 1..7) {
                if (i != 4) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .offset(y = (step * i) - 1.dp),
                        color = Color.Black.copy(0.3f)
                    )
                }
            }
        }
    }
}

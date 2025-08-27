package org.pruden.tablero.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.foundation.Canvas
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@Composable
fun MoveArrowOverlay(
    from: Pair<Int, Int>?,
    to: Pair<Int, Int>?,
    cellSize: Float,
    color: Color = Color(0x8899AABB),
    stroke: Float = 10f,
    head: Float = 26f,
    modifier: Modifier = Modifier
) {
    if(from == null || to == null) return

    Canvas(modifier) {
        val sx = (from.first + 0.5f) * cellSize
        val sy = (from.second + 0.5f) * cellSize
        val ex = (to.first + 0.5f) * cellSize
        val ey = (to.second + 0.5f) * cellSize

        val start = Offset(sx, sy)
        val end = Offset(ex, ey)

        drawLine(
            color = color,
            start = start,
            end = end,
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        val angle = atan2((end.y - start.y), (end.x - start.x))
        val p1 = Offset(
            x = end.x - head * cos(angle - PI.toFloat() / 8f),
            y = end.y - head * sin(angle - PI.toFloat() / 8f)
        )
        val p2 = Offset(
            x = end.x - head * cos(angle + PI.toFloat() / 8f),
            y = end.y - head * sin(angle + PI.toFloat() / 8f)
        )

        val path = Path().apply {
            moveTo(end.x, end.y)
            lineTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            close()
        }
        drawPath(path, color)
    }
}

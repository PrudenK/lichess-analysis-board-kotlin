package org.pruden.tablero.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
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
    stroke: Float = cellSize * 0.25f,
    head: Float = cellSize * 1f,
    divider: Float = 5.7f,
    modifier: Modifier = Modifier
) {
    if (from == null || to == null) return

    Canvas(modifier) {
        val sx = (from.first + 0.5f) * cellSize
        val sy = (from.second + 0.5f) * cellSize
        val ex = (to.first + 0.5f) * cellSize
        val ey = (to.second + 0.5f) * cellSize

        val start = Offset(sx, sy)
        val end = Offset(ex, ey)

        val angle = atan2((end.y - start.y), (end.x - start.x))
        val deg = (angle * 180f / PI.toFloat())

        val lineCut = head * 0.85f
        val endLine = Offset(
            end.x - cos(angle) * lineCut,
            end.y - sin(angle) * lineCut
        )

        drawLine(
            color = color,
            start = start,
            end = endLine,
            strokeWidth = stroke,
        )

        drawArc(
            color = color,
            startAngle = deg - 90f - 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(start.x - stroke / 2f, start.y - stroke / 2f),
            size = Size(stroke, stroke)
        )

        val p1 = Offset(
            x = end.x - head * cos(angle - PI.toFloat() / divider),
            y = end.y - head * sin(angle - PI.toFloat() / divider)
        )
        val p2 = Offset(
            x = end.x - head * cos(angle + PI.toFloat() / divider),
            y = end.y - head * sin(angle + PI.toFloat() / divider)
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

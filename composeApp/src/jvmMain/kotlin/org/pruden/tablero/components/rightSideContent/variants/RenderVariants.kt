package org.pruden.tablero.components.rightSideContent.variants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pruden.tablero.components.rightSideContent.movesTexts.VariantText
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.moves.MovesManager

@Composable
fun renderBranch(
    start: MoveNode,
    depth: Int,
    depthFactor: Int = 6
) {
    val spine = mutableListOf<MoveNode>()
    var current: MoveNode? = start
    while (current != null) {
        spine.add(current)
        val firstChildId = current.childrenIds.firstOrNull()
        current = Globals.movesNodesBuffer.value.find { it.id == firstChildId }
    }
    FlowRow(
        modifier = Modifier.padding(start = (depth * depthFactor).dp)
    ) {
        HorizontalDivider(modifier = Modifier.height(0.5.dp), color = Colors.textColor.copy(0.5f))

        for (n in spine) {
            VariantText(
                move = n,
                onClick = {
                    MovesManager.goToClickedMove(n)
                }
            )
        }
    }
    for (n in spine) {
        val siblings = n.childrenIds.drop(1).mapNotNull { id ->
            Globals.movesNodesBuffer.value.find { it.id == id }
        }
        if (siblings.isNotEmpty()) {
            Column(modifier = Modifier.padding(start = ((depth + 1) * depthFactor).dp)) {
                for (sib in siblings) {
                    renderBranch(sib, depth + 1)
                }
            }
        }
    }
}

@Composable
fun renderWhiteSiblingVariantsUnderBlack(parentBlack: MoveNode, depth: Int) {
    val children = parentBlack.childrenIds.mapNotNull { id ->
        Globals.movesNodesBuffer.value.find { it.id == id }
    }
    if (children.size <= 1) return
    val variants = children.drop(1)
    Column {
        for (variant in variants) {
            renderBranch(variant, depth)
        }
    }
}
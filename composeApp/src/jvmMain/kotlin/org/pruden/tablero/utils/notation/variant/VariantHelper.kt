package org.pruden.tablero.utils.notation.variant

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode

object VariantHelper {
    fun getParentOfWhite(white: MoveNode?): MoveNode?{
        return white?.parentId?.let { pid ->
            Globals.movesNodesBuffer.value.find { it.id == pid }
        }
    }

    fun getIfhasWhiteSiblings(white: MoveNode?, parentOfWhite: MoveNode?): Boolean{
        return  white != null &&
                (parentOfWhite?.isWhiteMove == false || parentOfWhite?.id == "root") && // CASE 3 root es el padre y tiene más de 1 hijo
                (parentOfWhite.childrenIds.size > 1)
    }

    fun getIfHasBlackSiblings(white: MoveNode?): Boolean{
        return white != null && white.childrenIds.size > 1
    }

    fun getPrincipalNodePairsWithMagnitude(): MutableList<Triple<Int, MoveNode?, MoveNode?>> {
        val principalNodesPair = mutableListOf<Triple<Int, MoveNode?, MoveNode?>>()
        var pendingWhite: MoveNode? = null

        for (id in Globals.orderNodesIds) {
            val node = Globals.movesNodesBuffer.value.find { it.id == id } ?: continue
            if (node.isPrincipalLine()) {
                if (node.isWhiteMove == true) {
                    if (pendingWhite != null) {
                        principalNodesPair.add(Triple(node.getStepsFromRoot(), pendingWhite, null))
                    }
                    pendingWhite = node
                } else {
                    if (pendingWhite != null) {
                        principalNodesPair.add(Triple(node.getStepsFromRoot(), pendingWhite, node))
                        pendingWhite = null
                    } else {
                        principalNodesPair.add(Triple(node.getStepsFromRoot(), null, node))
                    }
                }
            }
        }

        if (pendingWhite != null) {
            principalNodesPair.add(Triple(pendingWhite.getStepsFromRoot(), pendingWhite, null))
        }

        return principalNodesPair
    }
}
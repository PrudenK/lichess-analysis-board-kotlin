package org.pruden.tablero.models

import org.pruden.tablero.globals.Globals

data class MoveNode(
    val id: String,
    val parentId: String? = null,
    val childrenIds: MutableList<String> = mutableListOf(),
    var san: String? = null,
    val from: Pair<Int,Int>? = null,
    val to: Pair<Int,Int>? = null,
    val fen: String,
    var isActualMove: Boolean,
    var isWhiteMove: Boolean? = null
){
    fun getParentMove(nodes: List<MoveNode> = Globals.movesNodesBuffer.value) = nodes.find { it.id == this.parentId }


    fun getStepsFromRoot(nodes: List<MoveNode> = Globals.movesNodesBuffer.value): Int {
        val index = nodes.associateBy { it.id }
        var steps = 0
        var cur: MoveNode? = this
        while (cur != null && cur.id != "root") {
            cur = cur.parentId?.let { index[it] } ?: return -1
            steps++
        }
        return (steps + 1) / 2
    }

    fun isPrincipalLine(nodes: List<MoveNode> = Globals.movesNodesBuffer.value): Boolean {
        var cur: MoveNode? = this
        while (cur != null && cur.id != "root") {
            val parent = cur.getParentMove(nodes) ?: return false
            val first = parent.childrenIds.firstOrNull() ?: return false
            if (cur.id != first) return false
            cur = parent
        }
        return true
    }

    fun getMagnitudeOfVariant(nodes: List<MoveNode> = Globals.movesNodesBuffer.value): Int {
        val index = nodes.associateBy { it.id }
        var cur: MoveNode? = this
        var magnitude = 0
        while (cur != null && cur.id != "root") {
            val parent = cur.parentId?.let { index[it] } ?: break
            val posInParent = parent.childrenIds.indexOf(cur.id)
            if (posInParent > 0) {
                magnitude++
            }
            cur = parent
        }
        return magnitude
    }



}
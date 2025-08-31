package org.pruden.tablero.models

import org.pruden.tablero.globals.Globals

data class MoveNode(
    val id: String,
    val parentId: String? = null,
    val childrenIds: MutableList<String> = mutableListOf(),
    val san: String? = null,
    val from: Pair<Int,Int>? = null,
    val to: Pair<Int,Int>? = null,
    val fen: String,
    var isActualMove: Boolean,
    var isWhiteMove: Boolean? = null
){
    fun getParentMove() = Globals.movesNodesBuffer.value.find { it.id == this.parentId }


    fun getStepsFromRoot(): Int {
        val index = Globals.movesNodesBuffer.value.associateBy { it.id }
        var steps = 0
        var cur: MoveNode? = this
        while (cur != null && cur.id != "root") {
            cur = cur.parentId?.let { index[it] } ?: return -1
            steps++
        }
        return (steps + 1) / 2
    }

    fun isPrincipalLine(): Boolean {
        var cur: MoveNode? = this
        while (cur != null && cur.id != "root") {
            val parent = cur.getParentMove() ?: return false
            val first = parent.childrenIds.firstOrNull() ?: return false
            if (cur.id != first) return false
            cur = parent
        }
        return true
    }

    fun getMagnitudeOfVariant(): Int {
        val index = Globals.movesNodesBuffer.value.associateBy { it.id }
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
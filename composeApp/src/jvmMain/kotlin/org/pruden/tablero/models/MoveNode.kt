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
    var isActualMove: Boolean
){
    fun getParentMove() = Globals.movesNodesBuffer.value.find { it.id == this.parentId }


}
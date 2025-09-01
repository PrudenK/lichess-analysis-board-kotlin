package org.pruden.tablero.utils.nodes

data class NodePromotion(
    var san: String,
    var from: Pair<Int, Int>,
    var to: Pair<Int, Int>
)

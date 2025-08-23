package org.pruden.tablero.api.objects.request

data class EvalRequest(
    val fen: String,
    val depth: Int,
    val variants: Int
)

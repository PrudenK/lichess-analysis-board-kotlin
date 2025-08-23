package org.pruden.tablero.api.objects.response

data class ContinuationMove(
    val from: String?,
    val to: String?,
    val fromNumeric: String?,
    val toNumeric: String?
)
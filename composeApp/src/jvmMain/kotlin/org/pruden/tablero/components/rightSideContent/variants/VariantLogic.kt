package org.pruden.tablero.components.rightSideContent.variants

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.notation.variant.VariantHelper


@Composable // CASE 1, es el negro quien tiene hijos
fun WhiteSiblingVariantsUnderBlack(parentOfWhite: MoveNode?, hasWhiteSiblings: Boolean){
    if (parentOfWhite != null && hasWhiteSiblings) {
        renderWhiteSiblingVariantsUnderBlack(parentOfWhite, depth = 1)
    }
}

@Composable // CASE 2, el blanco tiene más de 1 hijo
fun WhiteChildrenVariants(white: MoveNode?){
    val hasBlackSiblings = VariantHelper.getIfHasBlackSiblings(white)

    if (hasBlackSiblings) {
        val children = white!!.childrenIds.mapNotNull { id ->
            Globals.movesNodesBuffer.value.find { it.id == id }
        }
        if (children.size > 1) {
            val variants = children.drop(1)
            Column {
                for (variant in variants) {
                    renderBranch(variant, depth = 1)
                }
            }
        }
    }
}
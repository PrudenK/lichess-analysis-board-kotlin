package org.pruden.tablero.components.rightSideContent

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pruden.tablero.components.rightSideContent.variants.*
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.notation.variant.VariantHelper
import org.pruden.tablero.utils.notation.variant.VariantHelper.getPrincipalNodePairsWithMagnitude

@Composable
fun MovesPanel(
    modifier: Modifier
) {
    val scrollState = rememberScrollState()

    key(Globals.refreshMovesPanel.value) {
        Row(
            modifier = modifier
                .width(350.dp)
                .padding(top = 10.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(color = Colors.secondary, RoundedCornerShape(6.dp))
            ) {
                ModuleToggle()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(color = Colors.backgroundMoves),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Divider(color = Colors.movesColor.copy(alpha = 0.5f))

                        val principalNodesPair = getPrincipalNodePairsWithMagnitude()

                        for (i in principalNodesPair.indices) {
                            val (steps, white, black) = principalNodesPair[i]

                            Column {
                                MainLineRowComponent(steps, white, black)

                                val parentOfWhite = VariantHelper.getParentOfWhite(white)
                                val hasWhiteSiblingsFromBlack = VariantHelper.getIfhasWhiteSiblings(white, parentOfWhite)

                                WhiteSiblingVariantsUnderBlack(parentOfWhite, hasWhiteSiblingsFromBlack)

                                if (black != null && hasWhiteSiblingsFromBlack) { // Para el CASE 1, el negro tiene hijos añadimos el salto ...
                                    MainLineRowComponentForCase1(steps, black)
                                }

                                WhiteChildrenVariants(white)
                            }
                        }
                    }

                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(scrollState),
                        modifier = Modifier.align(Alignment.CenterEnd),
                        style = LocalScrollbarStyle.current.copy(
                            unhoverColor = Colors.movesColor.copy(alpha = 0.3f),
                            hoverColor = Colors.movesColor.copy(alpha = 0.6f),
                            minimalHeight = 16.dp,
                            thickness = 8.dp
                        )
                    )
                }
                MovesManagerBottomPanel()
            }
        }
    }
}

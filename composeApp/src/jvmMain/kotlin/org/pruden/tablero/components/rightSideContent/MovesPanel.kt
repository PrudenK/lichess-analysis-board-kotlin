package org.pruden.tablero.components.rightSideContent

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.processNextEventInCurrentThread
import org.pruden.tablero.components.rightSideContent.movesTexts.MainLineText
import org.pruden.tablero.components.rightSideContent.variants.renderBranch
import org.pruden.tablero.components.rightSideContent.variants.renderWhiteSiblingVariantsUnderBlack
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.moves.MovesManager

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



                        for (i in principalNodesPair.indices) {
                            val (steps, white, black) = principalNodesPair[i]
                            val nextWhite = if (i + 1 < principalNodesPair.size) principalNodesPair[i + 1].second else null

                            println("\n----- triple nodes ---\n")
                            println(principalNodesPair[i])
                            println("\n___________\n")

                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min)
                                ) {
                                    Text(
                                        text = "$steps",
                                        fontSize = 18.sp,
                                        lineHeight = 28.sp,
                                        modifier = Modifier
                                            .weight(0.4f)
                                            .fillMaxWidth()
                                            .background(Colors.secondary)
                                            .padding(vertical = 4.dp),
                                        textAlign = TextAlign.Center,
                                        color = Colors.movesColor.copy(alpha = 0.5f)
                                    )

                                    VerticalDivider(
                                        color = Colors.movesColor.copy(alpha = 0.5f),
                                        modifier = Modifier.fillMaxHeight()
                                    )

                                    val parentOfWhite = white?.parentId?.let { pid ->
                                        Globals.movesNodesBuffer.value.find { it.id == pid }
                                    }

                                    println("\n---- Parent of white----\n")
                                    println(parentOfWhite)
                                    println("\n___________\n")

                                    val hasWhiteSiblingsFromBlack = white != null &&
                                            (parentOfWhite?.isWhiteMove == false || parentOfWhite?.id == "root") &&
                                            (parentOfWhite.childrenIds.size > 1)


                                    println("\n---- hasWhiteSiblingsFromBlack----\n")
                                    println(hasWhiteSiblingsFromBlack)
                                    println("\n___________\n")

                                    MainLineText(
                                        text = white?.san.orEmpty(),
                                        modifier = Modifier.weight(1f),
                                        textColor = Colors.movesColor,
                                        hoverColor = Colors.hoverColor,
                                        padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                        isThisMove = white?.isActualMove == true,
                                        onClick = {
                                            if(white != null){
                                                MovesManager.goToClickedMove(white)
                                            }
                                        }
                                    )

                                    MainLineText(
                                        text = when {
                                            hasWhiteSiblingsFromBlack -> "..."
                                            black != null -> black.san.orEmpty()
                                            else -> ""
                                        },
                                        modifier = Modifier.weight(1f),
                                        textColor = Colors.movesColor,
                                        hoverColor = Colors.hoverColor,
                                        padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                        isThisMove = when{
                                            hasWhiteSiblingsFromBlack -> false
                                            else -> black?.isActualMove == true
                                        },
                                        onClick = {
                                            if(black != null){
                                                MovesManager.goToClickedMove(black)
                                            }
                                        }
                                    )
                                }

                                val parentOfWhite = white?.parentId?.let { pid ->
                                    Globals.movesNodesBuffer.value.find { it.id == pid }
                                }

                                val hasWhiteSiblingsFromBlack = white != null &&
                                        (parentOfWhite?.isWhiteMove == false || parentOfWhite?.id == "root") && // CASE 3 root es el padre y tiene más de 1 hijo
                                        (parentOfWhite.childrenIds.size > 1)

                                if (parentOfWhite != null && hasWhiteSiblingsFromBlack) { // CASE 1, es el negro quien tiene hijos
                                    renderWhiteSiblingVariantsUnderBlack(parentOfWhite, depth = 10)
                                }




                                if (black != null && hasWhiteSiblingsFromBlack) { // Para el CASE 1, el negro tiene hijos añadimos el salto ...
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(IntrinsicSize.Min)
                                    ) {

                                        Text(
                                            text = "$steps",
                                            fontSize = 18.sp,
                                            lineHeight = 28.sp,
                                            modifier = Modifier
                                                .weight(0.4f)
                                                .fillMaxWidth()
                                                .background(Colors.secondary)
                                                .padding(vertical = 4.dp),
                                            textAlign = TextAlign.Center,
                                            color = Colors.movesColor.copy(alpha = 0.5f)
                                        )

                                        VerticalDivider(
                                            color = Colors.movesColor.copy(alpha = 0.5f),
                                            modifier = Modifier.fillMaxHeight()
                                        )

                                        MainLineText(
                                            text = "...",
                                            modifier = Modifier.weight(1f),
                                            textColor = Colors.movesColor,
                                            hoverColor = Colors.hoverColor,
                                            padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                            isThisMove = false,
                                            onClick = {

                                            }
                                        )

                                        MainLineText(
                                            text = black.san.orEmpty(),
                                            modifier = Modifier.weight(1f),
                                            textColor = Colors.movesColor,
                                            hoverColor = Colors.hoverColor,
                                            padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                            isThisMove = black.isActualMove,
                                            onClick = {
                                                MovesManager.goToClickedMove(black)
                                            }
                                        )
                                    }
                                }


                                val shouldRenderBlackVariantsHere = white != null && white.childrenIds.size > 1

                                // reivsar

                                println("\n---- shouldRenderBlackVariantsHere----\n")
                                println(shouldRenderBlackVariantsHere)
                                println(white)
                                println("\n___________\n")


                                if (shouldRenderBlackVariantsHere) { // CASE 2, el blanco tiene más de 1 hijo
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

package org.pruden.tablero.components.rightSideContent

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import org.pruden.tablero.components.rightSideContent.MovesTexts.MainLineText
import org.pruden.tablero.components.rightSideContent.MovesTexts.VariantText
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode

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

                        @Composable
                        fun renderBranch(start: MoveNode, depth: Int) {
                            val spine = mutableListOf<MoveNode>()
                            var current: MoveNode? = start
                            while (current != null) {
                                spine.add(current)
                                val firstChildId = current.childrenIds.firstOrNull()
                                current = Globals.movesNodesBuffer.value.find { it.id == firstChildId }
                            }
                            Row(modifier = Modifier.padding(start = (depth * 12).dp)) {
                                for (n in spine) {
                                    VariantText(move = n)
                                }
                            }
                            for (n in spine) {
                                val siblings = n.childrenIds.drop(1).mapNotNull { id ->
                                    Globals.movesNodesBuffer.value.find { it.id == id }
                                }
                                if (siblings.isNotEmpty()) {
                                    Column(modifier = Modifier.padding(start = ((depth + 1) * 12).dp)) {
                                        for (sib in siblings) {
                                            renderBranch(sib, depth + 1)
                                        }
                                    }
                                }
                            }
                        }

                        @Composable
                        fun renderWhiteSiblingVariantsUnderBlack(parentBlack: MoveNode, depth: Int) {
                            val children = parentBlack.childrenIds.mapNotNull { id ->
                                Globals.movesNodesBuffer.value.find { it.id == id }
                            }
                            if (children.size <= 1) return
                            val variants = children.drop(1)
                            Column {
                                for (variant in variants) {
                                    renderBranch(variant, depth)
                                }
                            }
                        }

                        for (i in principalNodesPair.indices) {
                            val (steps, white, black) = principalNodesPair[i]
                            val nextWhite = if (i + 1 < principalNodesPair.size) principalNodesPair[i + 1].second else null

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

                                    val hasWhiteSiblingsFromBlack = white != null &&
                                            parentOfWhite?.isWhiteMove == false &&
                                            (parentOfWhite.childrenIds.size > 1)

                                    MainLineText(
                                        text = white?.san.orEmpty(),
                                        modifier = Modifier.weight(1f),
                                        textColor = Colors.movesColor,
                                        hoverColor = Colors.hoverColor,
                                        padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                        isThisMove = white?.isActualMove == true,
                                        onClick = {}
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
                                        onClick = {}
                                    )
                                }

                                val parentOfWhite = white?.parentId?.let { pid ->
                                    Globals.movesNodesBuffer.value.find { it.id == pid }
                                }

                                val hasWhiteSiblingsFromBlack = white != null &&
                                        parentOfWhite?.isWhiteMove == false &&
                                        (parentOfWhite.childrenIds.size > 1)

                                if (parentOfWhite != null && hasWhiteSiblingsFromBlack) {
                                    renderWhiteSiblingVariantsUnderBlack(parentOfWhite, depth = 1)
                                }

                                val shouldRenderBlackVariantsHere = black != null &&
                                        !(black.childrenIds.isNotEmpty() &&
                                                nextWhite != null &&
                                                black.childrenIds.firstOrNull() == nextWhite.id)

                                if (shouldRenderBlackVariantsHere) {
                                    val children = black!!.childrenIds.mapNotNull { id ->
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

                                if (black != null && hasWhiteSiblingsFromBlack) {
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

                                            }
                                        )
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

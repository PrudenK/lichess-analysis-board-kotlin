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
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.moves.MovesManager
import kotlin.collections.chunked

@Composable
fun MovesPanel(
    modifier: Modifier
) {
    val scrollState = rememberScrollState()
    val hasOverflow = scrollState.maxValue > 0

    key(Globals.refreshMovesPanel.value) {
        Row(
            modifier = modifier
                .width(350.dp)
                .padding(
                    top = 10.dp
                ),
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



                        val pairs = Globals.movesBufferNotation.value.chunked(2)


                        for ((i, pair) in pairs.withIndex()) {
                            val whiteSan = pair.getOrNull(0)?.san.orEmpty()
                            val blackSan = pair.getOrNull(1)?.san.orEmpty()

                            val isThisWhiteMove = pair.getOrNull(0)?.isActualMove ?: false
                            val isThisBlackMove = pair.getOrNull(1)?.isActualMove ?: false

                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                            ) {
                                Text(
                                    text = "${i + 1}",
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
                                    text = whiteSan,
                                    modifier = Modifier.weight(1f),
                                    textColor = Colors.movesColor,
                                    hoverColor = Colors.hoverColor,
                                    padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                    isThisMove = isThisWhiteMove,
                                    onClick = {
                                        MovesManager.goToClickedMove(pair[0])
                                    }
                                )

                                MainLineText(
                                    text = blackSan,
                                    modifier = Modifier.weight(1f).padding(end = if(hasOverflow) 8.dp else 0.dp),
                                    textColor = Colors.movesColor,
                                    hoverColor = Colors.hoverColor,
                                    padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                    isThisMove = isThisBlackMove,
                                    onClick = {
                                        if(pair.getOrNull(1) != null) {
                                            MovesManager.goToClickedMove(pair[1])
                                        }
                                    }
                                )




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


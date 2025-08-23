package org.pruden.tablero.components.sideContent

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.components.custom.IconSwitch
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.moves.MovesManager
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.moves_all
import tableroajedrez.composeapp.generated.resources.moves_one
import kotlin.collections.chunked

@Composable
fun MovesPanel(
    modifier: Modifier
) {
    val backgroundMoves = Color(0xFF262421)
    val movesColor = Color(0xFFc0c0c0)
    val hoverColor = Color(0xFF3692e7)
    val scrollState = rememberScrollState()
    val secondary = Color(0xFF383632)

    val pairs = Globals.movesBufferNotation.value.chunked(2)

    key(Globals.refreshMovesPanel.value) {
        Row(
            modifier = modifier
                .width(350.dp)
                .padding(
                    top = 10.dp
                ),
        ) {
            Column(
                modifier = Modifier.background(color = secondary)
            ) {


                Box(
                    modifier = Modifier.padding(15.dp)
                ){
                    IconSwitch(
                        checked = Globals.isModuleActivated.value,
                        onCheckedChange = {
                            Globals.isModuleActivated.value = it
                        },
                        modifier = Modifier.scale(1.3f)
                    )
                }




                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(color = backgroundMoves),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {

                        Divider(color = movesColor.copy(alpha = 0.5f))

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
                                        .background(secondary)
                                        .padding(vertical = 4.dp),
                                    textAlign = TextAlign.Center,
                                    color = movesColor.copy(alpha = 0.5f)
                                )


                                VerticalDivider(
                                    color = movesColor.copy(alpha = 0.5f),
                                    modifier = Modifier.fillMaxHeight()
                                )

                                TextWithHover(
                                    text = whiteSan,
                                    modifier = Modifier.weight(1f),
                                    textColor = movesColor,
                                    hoverColor = hoverColor,
                                    padding = PaddingValues(start = 12.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                    isThisMove = isThisWhiteMove,
                                    onClick = {
                                        MovesManager.goToClickedMove(pair[0])
                                    }
                                )

                                TextWithHover(
                                    text = blackSan,
                                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                                    textColor = movesColor,
                                    hoverColor = hoverColor,
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
                            unhoverColor = movesColor.copy(alpha = 0.3f),
                            hoverColor = movesColor.copy(alpha = 0.6f),
                            minimalHeight = 16.dp,
                            thickness = 8.dp
                        )
                    )
                }

                Box {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        IconButton(
                            onClick = {
                                MovesManager.goToStart()
                            }
                        ) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.moves_all),
                                contentDescription = null,
                                tint = movesColor,
                                modifier = Modifier.size(24.dp).rotate(180f)
                            )
                        }

                        IconButton(
                            onClick = {
                                MovesManager.stepBack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.moves_one),
                                contentDescription = null,
                                tint = movesColor,
                                modifier = Modifier.size(24.dp).rotate(180f)
                            )
                        }

                        IconButton(
                            onClick = {
                                MovesManager.stepForward()
                            }
                        ) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.moves_one),
                                contentDescription = null,
                                tint = movesColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                MovesManager.goToEnd()
                            }
                        ) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.moves_all),
                                contentDescription = null,
                                tint = movesColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextWithHover(
    text: String,
    modifier: Modifier = Modifier,
    hoverColor: Color,
    textColor: Color,
    padding: PaddingValues = PaddingValues(all = 0.dp),
    isThisMove: Boolean = false,
    onClick: () -> Unit = { },
    fontSize: TextUnit = 20.sp
) {
    val isHovered = remember { mutableStateOf(false) }
    Text(
        text = text,
        modifier = modifier
            .background(if (isHovered.value && text.isNotEmpty()) hoverColor else if(isThisMove) hoverColor.copy(0.5f) else Color.Transparent)
            .padding(padding)
            .pointerMoveFilter(
                onEnter = {
                    isHovered.value = true
                    false
                },
                onExit = {
                    isHovered.value = false
                    false
                }
            ).clickable {
                onClick()
            },
        color = textColor,
        fontWeight = if (isThisMove) FontWeight.Bold else FontWeight.Normal,
        fontSize = fontSize
    )
}


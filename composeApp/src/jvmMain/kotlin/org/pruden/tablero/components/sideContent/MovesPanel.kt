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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.components.custom.IconSwitch
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.moves.MovesManager
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.moves_all
import tableroajedrez.composeapp.generated.resources.moves_one
import java.util.*
import kotlin.collections.chunked

@Composable
fun MovesPanel(
    modifier: Modifier
) {
    val pairs = Globals.movesBufferNotation.value.chunked(2)
    val scrollState = rememberScrollState()

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



                if (Globals.isModuleActivated.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Colors.moduleActivateColor, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                    )
                }
                Box(
                    modifier = Modifier.padding(15.dp)
                ){
                    Row {
                        IconSwitch(
                            checked = Globals.isModuleActivated.value,
                            onCheckedChange = {
                                Globals.isModuleActivated.value = it
                            },
                            modifier = Modifier.scale(1.2f).padding(top = 4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .padding(start = 22.dp)
                                .height(32.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (Globals.isModuleActivated.value) {
                                val text = String.format(Locale.US, "%.1f", Globals.valoration.value / 100f)
                                Text(
                                    text = "${if (Globals.valoration.value > 0) "+" else ""}$text",
                                    fontSize = 24.sp,
                                    color = Colors.textColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }





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

                                TextWithHover(
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

                                TextWithHover(
                                    text = blackSan,
                                    modifier = Modifier.weight(1f).padding(end = 8.dp),
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
                                tint = Colors.movesColor,
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
                                tint = Colors.movesColor,
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
                                tint = Colors.movesColor,
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
                                tint = Colors.movesColor,
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


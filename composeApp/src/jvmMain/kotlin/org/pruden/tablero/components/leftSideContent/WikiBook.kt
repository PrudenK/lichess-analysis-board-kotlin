package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.api.constants.ApiWikiBook
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.wikibook_caret

@Composable
fun WikiBook() {
    val backgroundColor = Color(0xFF373531)
    var bookIsOpen by remember { mutableStateOf(true) }
    var extractHtml by remember { mutableStateOf("") }

    val sanKey = Globals.movesBufferNotation.value.joinToString("|") { it.san }

    LaunchedEffect(sanKey) {
        val formatted = buildString {
            var moveNumber = 1
            for ((index, move) in Globals.movesBufferNotation.value.map { it.san }.withIndex()) {
                if (index % 2 == 0) {
                    append("${moveNumber}._$move")
                } else {
                    append("${moveNumber}...$move")
                    moveNumber++
                }
                if (index < Globals.movesBufferNotation.value.size - 1) append("/")
            }
        }

        val titles = "Chess_Opening_Theory/$formatted"
        val res = ApiWikiBook.wikiBookService.getExtract(titles)
        extractHtml = res.query?.pages?.values?.firstOrNull()?.extract.orEmpty()
    }

    if(extractHtml.isNotEmpty()){
        Box(modifier = Modifier.width(Globals.leftSideContentWidth)) {
            if(bookIsOpen){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor, RoundedCornerShape(12.dp))
                        .align(Alignment.TopStart)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Spacer(Modifier.height(10.dp))


                        HtmlNative(extractHtml, Modifier.fillMaxWidth().fillMaxHeight())

                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-18).dp)
                    .zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .background(if(bookIsOpen) backgroundColor else Colors.backgroundGeneral, RoundedCornerShape(6.dp))
                            .border(width = if(bookIsOpen) 0.dp else 0.2.dp, color = if(bookIsOpen) Color.Transparent else Colors.textColor.copy(0.4f), shape = RoundedCornerShape(6.dp))
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable { bookIsOpen = !bookIsOpen },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("WikiBook", color = Colors.textColor, fontSize = 16.sp, modifier = Modifier.padding(2.dp))

                            Spacer(Modifier.width(10.dp))

                            Icon(
                                painter = painterResource(resource = Res.drawable.wikibook_caret),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp).rotate(if (bookIsOpen) 180f else 0f)
                                    .padding(top = if (bookIsOpen) 0.dp else 4.dp, bottom = if (bookIsOpen) 4.dp else 0.dp),
                                tint = Colors.textColor
                            )
                        }
                    }
                    Spacer(Modifier.width(48.dp))
                }
            }
        }
    }
}

package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.pruden.tablero.globals.Colors
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.moves.MoveCalculator
import org.pruden.tablero.utils.nodes.PGNHandler
import org.pruden.tablero.utils.topBar.TopBarHandler
import tableroajedrez.composeapp.generated.resources.Res
import tableroajedrez.composeapp.generated.resources.bP
import tableroajedrez.composeapp.generated.resources.github
import java.awt.Desktop
import java.net.URI

@Composable
fun TopBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(56.dp)
            .background(Colors.backgroundGeneral).padding(top = 10.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextButton(
            onClick = {
                Desktop.getDesktop().browse(URI("https://github.com/PrudenK"))
            },
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PrudenK",
                    fontSize = 24.sp,
                    color = Colors.textColor
                )

                Spacer(Modifier.width(16.dp))

                Icon(
                    painter = painterResource(resource = Res.drawable.github),
                    contentDescription = null,
                    tint = Colors.textColor
                )
            }
        }

        TextButton(
            onClick = {
                TopBarHandler.restartBoard()
            },
        ){
            Text(
                text = "Reiniciar tablero",
                color = Colors.textColor,
                fontSize = 16.sp
            )
        }

        TextButton(
            onClick = {
                Globals.isBoardRotated.value = !Globals.isBoardRotated.value
            },
        ){
            Text(
                text = "Girar tablero",
                color = Colors.textColor,
                fontSize = 16.sp
            )
        }


        TextButton(
            onClick = {
                for(l in Globals.movesNodesBuffer.value){
                    println(l)
                }

                val prueba5 = "MoveNode(id=root, parentId=null, childrenIds=[de435a7b-ed80-4b39-9f59-326bbc6817d0, ecb802b7-eeba-4d7d-9ea8-7c207bd090ac, c12276d1-b599-40fb-b37d-ab774d044f46, 213d0883-bc3b-4694-9a06-38b5868ec8e7, 5b22c390-c0db-484b-b615-86adf015001d], san=null, from=null, to=null, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=null)\n" +
                        "MoveNode(id=de435a7b-ed80-4b39-9f59-326bbc6817d0, parentId=root, childrenIds=[], san=d4, from=(3, 6), to=(3, 4), fen=rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 1, isActualMove=false, isWhiteMove=true)\n" +
                        "MoveNode(id=ecb802b7-eeba-4d7d-9ea8-7c207bd090ac, parentId=root, childrenIds=[], san=e4, from=(4, 6), to=(4, 4), fen=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                        "MoveNode(id=c12276d1-b599-40fb-b37d-ab774d044f46, parentId=root, childrenIds=[], san=c4, from=(2, 6), to=(2, 4), fen=rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR w KQkq - 0 2, isActualMove=false, isWhiteMove=true)\n" +
                        "MoveNode(id=213d0883-bc3b-4694-9a06-38b5868ec8e7, parentId=root, childrenIds=[], san=b4, from=(1, 6), to=(1, 4), fen=rnbqkbnr/pppppppp/8/8/1P6/8/P1PPPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                        "MoveNode(id=5b22c390-c0db-484b-b615-86adf015001d, parentId=root, childrenIds=[5ed15249-14d6-4c42-9174-878ff0bfdb53], san=a4, from=(0, 6), to=(0, 4), fen=rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 3, isActualMove=false, isWhiteMove=true)\n" +
                        "MoveNode(id=5ed15249-14d6-4c42-9174-878ff0bfdb53, parentId=5b22c390-c0db-484b-b615-86adf015001d, childrenIds=[], san=e5, from=(4, 1), to=(4, 3), fen=rnbqkbnr/pppp1ppp/8/4p3/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 4, isActualMove=true, isWhiteMove=false)"


                fun parseMoveNodes(text: String): List<MoveNode> {
                    val lineRegex = Regex("""^MoveNode\(id=(.*?),\s*parentId=(.*?),\s*childrenIds=\[(.*?)\],\s*san=(.*?),\s*from=(.*?),\s*to=(.*?),\s*fen=(.*?),\s*isActualMove=(.*?),\s*isWhiteMove=(.*?)\)$""")
                    fun parsePair(s: String): Pair<Int, Int>? {
                        val t = s.trim()
                        if (t == "null") return null
                        val m = Regex("""\(([-]?\d+)\s*,\s*([-]?\d+)\)""").find(t) ?: return null
                        return Pair(m.groupValues[1].toInt(), m.groupValues[2].toInt())
                    }
                    return text.lineSequence()
                        .map { it.trim() }
                        .filter { it.startsWith("MoveNode(") }
                        .mapNotNull { line ->
                            val m = lineRegex.find(line) ?: return@mapNotNull null
                            val id = m.groupValues[1].trim()
                            val parentId = m.groupValues[2].trim().let { if (it == "null") null else it }
                            val children = m.groupValues[3].trim().let { if (it.isEmpty()) emptyList() else it.split(Regex("\\s*,\\s*")) }.toMutableList()
                            val san = m.groupValues[4].trim().let { if (it == "null") null else it }
                            val from = parsePair(m.groupValues[5])
                            val to = parsePair(m.groupValues[6])
                            val fen = m.groupValues[7].trim()
                            val isActualMove = m.groupValues[8].trim().toBooleanStrictOrNull() ?: false
                            val isWhiteMove = m.groupValues[9].trim().let { if (it == "null") null else it.toBooleanStrict() }
                            MoveNode(id = id, parentId = parentId, childrenIds = children, san = san, from = from, to = to, fen = fen, isActualMove = isActualMove, isWhiteMove = isWhiteMove)
                        }.toList()
                }

                println(PGNHandler.nodeMovesToPgn())
            },
        ){
            Text(
                text = "Aux",
                color = Colors.textColor,
                fontSize = 16.sp
            )
        }
    }
}
fun positionToChessNotation(position: Pair<Int, Int>): String {
    val l = "abcdefgh"
    return l[position.first].toString() + (7 - position.second +1)
}

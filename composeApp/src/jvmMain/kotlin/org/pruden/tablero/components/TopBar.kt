package org.pruden.tablero.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.utils.moves.MoveCalculator
import org.pruden.tablero.utils.notation.FenToChessBoard
import org.pruden.tablero.utils.topBar.TopBarHandler

@Composable
fun TopBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TextButton(
            onClick = {
                TopBarHandler.restartBoard()
            },
        ){
            Text("Reiniciar tablero")
        }
        TextButton(
            onClick = {
                TopBarHandler.printBoard()
            },
        ){
            Text("Print")
        }

        TextButton(
            onClick = {
                println(MoveCalculator.calculateCellsControledByOponent(org.pruden.tablero.models.Color.Black).map { positionToChessNotation(it) })
            },
        ){
            Text("Cells controled")
        }

        TextButton(
            onClick = {
                for(m in Globals.movesBufferNotation.value.map { it.fen }){
                    println(m)
                }
                println("_______________________________")
            },
        ){
            Text("Fen")
        }

        TextButton(
            onClick = {
                FenToChessBoard.setBoardFromFen("rnbqk2r/ppppbppp/4pn2/8/8/4PN2/PPPPBPPP/RNBQ1RK1 b kq - 3 4")
            },
        ){
            Text("LoadFen")
        }
    }
}
fun positionToChessNotation(position: Pair<Int, Int>): String {
    val l = "abcdefgh"
    return l[position.first].toString() + (7 - position.second +1)
}

package org.pruden.tablero.utils.nodes

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode
import org.pruden.tablero.utils.notation.FenConverter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object MovesNodesManager {
    @OptIn(ExperimentalUuidApi::class)
    fun addMove(
        san: String,
        from: Pair<Int, Int>,
        to: Pair<Int, Int>
    ){
        val list = Globals.movesNodesBuffer.value.toMutableList()

        val actualMoveBefore = list.find { it.isActualMove }

        if(actualMoveBefore != null){

            for(id in actualMoveBefore.childrenIds){
                val mov = list.find { it.id == id }!!

                println("MOVEEEEE SAN: ${mov.san} ${san}")

                if ((mov.from == from && mov.to == to) || mov.san == san ) {



                    list.forEach { it.isActualMove = false }
                    mov.isActualMove = true

                    Globals.actualNodeMoveId = mov.id

                    return
                }
            }

            val newMoveId = Uuid.random().toString()

            actualMoveBefore.childrenIds.add(newMoveId)

            list.forEach { it.isActualMove = false }

            val newMove = MoveNode(
                id = newMoveId,
                parentId = actualMoveBefore.id,
                san = san,
                from = from,
                to = to,
                fen = FenConverter.chessBoardToFen(Globals.chessBoard),
                isActualMove = true,
                isWhiteMove = Globals.isWhiteMove.value
            )

            Globals.actualNodeMoveId = newMoveId

            list.add(newMove)

            Globals.movesNodesBuffer.value = list
        }
    }


    fun modifyLastMoveNode(adder : String){
        if (Globals.movesNodesBuffer.value.isEmpty()) return

        val lastMove = Globals.movesNodesBuffer.value.find { it.id == Globals.actualNodeMoveId }!!
        val index = Globals.movesNodesBuffer.value.indexOf(lastMove)

        if(adder == "#" || adder == "+" && lastMove.san!!.endsWith("+")){
            lastMove.san = lastMove.san!!.replace("+", "")
        }

        if(!(adder.startsWith("=") && lastMove.san!!.contains(adder))){
            lastMove.san += adder
        }

        Globals.movesNodesBuffer.value[index] = lastMove
    }



}
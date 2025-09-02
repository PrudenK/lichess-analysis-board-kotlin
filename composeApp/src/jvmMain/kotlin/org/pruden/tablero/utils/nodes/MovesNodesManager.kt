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
                fen = FenConverter.chessBoardToFen(Globals.chessBoard, 90),
                isActualMove = true,
                isWhiteMove = Globals.isWhiteMove.value
            )

            Globals.actualNodeMoveId = newMoveId

            list.add(newMove)

            Globals.movesNodesBuffer.value = list
        }
    }


    @OptIn(ExperimentalUuidApi::class)  // TODO refactor
    fun modifyLastMoveNode(adder : String){
        val list = Globals.movesNodesBuffer.value.toMutableList()

        if (list.isEmpty()) return

        val actualMove = list.find { it.id == Globals.actualNodeMoveId }!!
        val index = list.indexOf(actualMove)

        if(adder == "#" || adder == "+" && actualMove.san!!.endsWith("+")){
            actualMove.san = actualMove.san!!.replace("+", "")
        }

        var appendMovePromotion = false

        if(!(adder.startsWith("=") && actualMove.san!!.contains(adder))){
            if(adder.startsWith("=")){
                if(actualMove.san!!.contains("=")){
                    if(actualMove.san!!.contains(adder)){
                        //NADA
                    }else{
                        appendMovePromotion = true

                        val parentLasMove = list.find { it.id == actualMove.parentId }!!
                        val newMoveId = Uuid.random().toString()

                        for(p in parentLasMove.childrenIds){
                            val n = Globals.movesNodesBuffer.value.find { it.id == p }!!

                            if(n.san!!.contains(adder)) return
                        }


                        parentLasMove.childrenIds.add(newMoveId)

                        list.forEach { it.isActualMove = false }

                        val newMove = MoveNode(
                            id = newMoveId,
                            parentId = parentLasMove.id,
                            san = actualMove.san!!.split("=")[0] + adder,
                            from = actualMove.from,
                            to = actualMove.to,
                            fen = FenConverter.chessBoardToFen(Globals.chessBoard, if(parentLasMove.isWhiteMove!!) parentLasMove.getStepsFromRoot() + 1 else parentLasMove.getStepsFromRoot()),
                            isActualMove = true,
                            isWhiteMove = Globals.isWhiteMove.value
                        )

                        Globals.actualNodeMoveId = newMoveId

                        list.add(newMove)

                        Globals.movesNodesBuffer.value = list
                    }
                }else{
                    actualMove.san += adder
                }
            }else{
                actualMove.san += adder
            }
        }

        if(!appendMovePromotion){
            Globals.movesNodesBuffer.value[index] = actualMove
        }
    }

    fun updateLastMoveFen(){
        val move = Globals.movesNodesBuffer.value.last()

        val playNumber = when{
            move.id  == "root" -> 1
            move.isWhiteMove!! -> move.getStepsFromRoot()
            else ->  move.getStepsFromRoot() + 1
        }

        move.fen = FenConverter.chessBoardToFen(Globals.chessBoard, playNumber)
    }

}
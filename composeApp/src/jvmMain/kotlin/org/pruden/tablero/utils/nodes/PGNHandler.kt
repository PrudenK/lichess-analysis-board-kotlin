package org.pruden.tablero.utils.nodes

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode

object PGNHandler {
    fun nodeMovesToPgn(nodes: List<MoveNode> = Globals.movesNodesBuffer.value): String {
        val pila = mutableListOf<String>()
        val pilaHistorica = mutableMapOf<String, Boolean>()

        for(n in nodes){
            println(n.san + " ${n.getMagnitudeOfVariant()}")
        }

        var resultado = ""

        fun subVariants(lista: MutableList<String>, vieneDePila: Boolean = false){
            for((index, id) in lista.withIndex()){
                val node = nodes.find { it.id == id }!!
                if(node.childrenIds.isNotEmpty() && index != 1 && lista.size > 1){
                    if(index == 0){
                        val subList = lista.subList(2, lista.size).reversed()

                        pila.add(id)
                        pilaHistorica[id] = false

                        if(node.isWhiteMove!!){
                            resultado += " ${node.getStepsFromRoot()}. ${node.san}"
                        }else{
                            resultado += " ${node.san}"
                        }

                        for(subId in subList){
                            pila.add(subId)
                            pilaHistorica[subId] = true
                        }
                    }
                }else{
                    if(!(pilaHistorica.contains(id) && !vieneDePila)){
                        if(!vieneDePila){
                            if(index != 0){
                                if(node.isWhiteMove!!){
                                    resultado += " (${node.getStepsFromRoot()}. ${node.san}"
                                }else{
                                    resultado += " (${node.getStepsFromRoot()}... ${node.san}"
                                }
                            }else{
                                if(pilaHistorica.contains(node.parentId) && pilaHistorica[node.parentId] == false){

                                    if(node.isWhiteMove!!){
                                        resultado += " ${node.getStepsFromRoot()}. ${node.san}"
                                    }else{
                                        resultado += " ${node.getStepsFromRoot()}... ${node.san}"
                                    }
                                }else{
                                    if(node.isWhiteMove!!){
                                        resultado += " ${node.getStepsFromRoot()}. ${node.san}"
                                    }else{
                                        resultado += " ${node.san}"
                                    }
                                }
                            }
                        }else{
                            if(pilaHistorica[id] == true){
                                if(node.isWhiteMove!!){
                                    resultado += " (${node.getStepsFromRoot()}. ${node.san}"
                                }else{
                                    resultado += " (${node.getStepsFromRoot()}... ${node.san}"
                                }
                            }
                        }

                        if(node.childrenIds.isNotEmpty()){
                            subVariants(node.childrenIds, false)
                        }else{
                            if(index == lista.size -1){
                                if(pila.isNotEmpty()){
                                    val last = pila.removeLast()

                                    var interactions = node.getMagnitudeOfVariant() - Globals.movesNodesBuffer.value.find { it.id == last }!!.getMagnitudeOfVariant()

                                    if(interactions == 0) interactions = 1

                                    repeat(interactions){
                                        resultado += ")"
                                    }

                                    subVariants(mutableListOf(last), true)
                                }
                            }
                        }
                    }
                }
            }
        }

        subVariants(nodes[0].childrenIds)

        resultado = resultado.replace(" )", ")").replace(Regex("\\s+"), " ").trim()
        println(resultado)
        return "Finish"
    }
}

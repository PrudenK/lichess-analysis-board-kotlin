package org.pruden.tablero.utils.nodes

import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.MoveNode

object PGNHandler {
    fun nodeMovesToPgn(nodes: List<MoveNode> = Globals.movesNodesBuffer.value): String {
        val stack = mutableListOf<String>()
        val historyStack = mutableMapOf<String, Boolean>()

        var result = ""
        var pgnWithIds = ""

        fun subVariants(movesList: MutableList<String>, comesFromStack: Boolean = false){
            for((index, id) in movesList.withIndex()){
                val node = nodes.find { it.id == id }!!

                if(index == 0 && movesList.size > 1){
                    val subList = movesList.subList(2, movesList.size).reversed()

                    if(hasChildren(node)){
                        stack.add(id)
                        historyStack[id] = false
                    }

                    result += if(result.isNotEmpty() && result.last() == ')'){
                        " ${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.san}"
                    }else{
                        " ${if(node.isWhiteMove!!) node.getStepsFromRoot(nodes).toString()+"." else ""} ${node.san}"
                    }



                    // TODO
                    pgnWithIds += if(pgnWithIds.isNotEmpty() && pgnWithIds.last() == ')'){
                        " ${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.id}"
                    }else{
                        " ${if(node.isWhiteMove!!) node.getStepsFromRoot(nodes).toString()+"." else ""} ${node.id}"
                    }

                    for(subId in subList){
                        stack.add(subId)
                        historyStack[subId] = true
                    }
                }else{
                    if(isNotInHistoryOrFromStack(id, comesFromStack, historyStack)){
                        if(comesFromStack){
                            if(historyStack[id] == true){
                                result += " (${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.san}"


                                // TODO
                                pgnWithIds += " (${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.id}"

                            }
                        }else{
                            result += if(isFirstMove(index)){
                                if(comesFromStackAndIsPrincipalVariant(historyStack, node)){
                                    " ${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.san}"
                                }else{
                                    " ${if(node.isWhiteMove!!) node.getStepsFromRoot(nodes).toString()+"." else ""} ${node.san}"
                                }
                            }else{
                                " (${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.san}"
                            }

                            // TODO
                            pgnWithIds += if(isFirstMove(index)){
                                if(comesFromStackAndIsPrincipalVariant(historyStack, node)){
                                    " ${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.id}"
                                }else{
                                    " ${if(node.isWhiteMove!!) node.getStepsFromRoot(nodes).toString()+"." else ""} ${node.id}"
                                }
                            }else{
                                " (${node.getStepsFromRoot(nodes)}${if(node.isWhiteMove!!) "." else "..."} ${node.id}"
                            }
                        }

                        if(hasChildren(node)){
                            subVariants(node.childrenIds, false)
                        }else{
                            if(isLastElement(index, movesList)){
                                if(stack.isNotEmpty()){
                                    val last = stack.removeLast()

                                    result += getClosingParentheses(node, last, nodes)

                                    // TODO
                                    pgnWithIds += getClosingParentheses(node, last, nodes)

                                    subVariants(mutableListOf(last), true)
                                }else{
                                    repeat(node.getMagnitudeOfVariant(nodes)){
                                        result += ")"

                                        //TODO
                                        pgnWithIds += ")"
                                    }
                                }
                            }else{
                                if(
                                    index == 1
                                ){
                                    if(stack.isNotEmpty()){
                                        val last = stack.removeLast()
                                        result += getClosingParentheses(node, last, nodes)

                                        // TODO
                                        pgnWithIds += getClosingParentheses(node, last, nodes)

                                        subVariants(mutableListOf(last), true)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        subVariants(nodes[0].childrenIds)

        Globals.pgnWithIds = normalizeWhitespace(pgnWithIds)

        println(Globals.pgnWithIds)
        println(normalizeWhitespace(result))

        return normalizeWhitespace(result)
    }


    private fun isNotInHistoryOrFromStack(id: String, comesFromStack: Boolean, historyStack: MutableMap<String, Boolean>): Boolean {
        return !(historyStack.contains(id) && !comesFromStack)
    }

    private fun isLastElement(i: Int, list: MutableList<String>) = i == list.size -1
    private fun hasChildren(node: MoveNode): Boolean = node.childrenIds.isNotEmpty()
    private fun isFirstMove(i: Int) = i == 0
    private fun comesFromStackAndIsPrincipalVariant(historyStack: MutableMap<String, Boolean>, node: MoveNode): Boolean{
        return historyStack.contains(node.parentId) && historyStack[node.parentId] == false
    }
    private fun getClosingParentheses(node: MoveNode, last: String, nodes: List<MoveNode>): String{
        var result = ""

        val to = nodes.find { it.id == last }!!

        var interactions = node.getMagnitudeOfVariant(nodes) - to.getMagnitudeOfVariant(nodes)

        val indexToInParentChilds = calculateIndexOfToNodeInHisParentNodeListOfChilds(to, nodes, last)
        if(indexToInParentChilds != 0) interactions++

        repeat(interactions){
            result += ")"
        }

        return result
    }
    private fun normalizeWhitespace(result: String): String{
        return result.replace(" )", ")").replace(Regex("\\s+"), " ").trim()
    }

    private fun calculateIndexOfToNodeInHisParentNodeListOfChilds(to: MoveNode, nodes: List<MoveNode>, last: String ): Int{
        val parentTo = to.parentId
        val parentToNode = nodes.find { it.id == parentTo }!!
        val indexToInParentChilds = parentToNode.childrenIds.indexOf(last)

        return indexToInParentChilds
    }
}

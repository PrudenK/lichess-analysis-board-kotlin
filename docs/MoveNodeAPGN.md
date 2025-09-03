### MoveNode a PGN

En primer lugar, para lograr una estructura de movimientos que permita añadir variantes es necesario crear un esquema de nodos entre estos movmientos, o al menos esa ha sido la opción que he escogido. Para ello se ha usado el siguiente objeto: 

```
data class MoveNode(
    val id: String,
    val parentId: String? = null,
    val childrenIds: MutableList<String> = mutableListOf(),
    var san: String? = null,
    val from: Pair<Int,Int>? = null,
    val to: Pair<Int,Int>? = null,
    var fen: String,
    var isActualMove: Boolean,
    var isWhiteMove: Boolean? = null
)
```

Al crear el tablero, se añade el siguiente objeto MoveNode a la lista `Globals.movesNodesBuffer`, la cual se encarga de almacenar los movimientos: 
```
Globals.movesNodesBuffer.value.add(
        MoveNode(
            id = "root",
            fen = FenConverter.chessBoardToFen(Globals.chessBoard, 1),
            isActualMove = true
        )
    )
```

Este objeto es necesario, ya que marca el inicio de la partida con el FEN inicial.

La función que se encarga de convertir la lista a notación PGN es: `PGNHandler.nodeMovesToPgn(nodes: List<MoveNode> = Globals.movesNodesBuffer.value): String`

Para ello, empieza recorriendo los hijos de root en su primera iteración: `subVariants(nodes[0].childrenIds)`

Lo más importante y lo primero que se tuvo en cuenta en este algoritmo fue determinar el orden en el que se iban procesando los nodos,
si el primero elemento de la lista (`index == 0`) tiene hermanos (se cumple `movesList.size > 1`) se realizarán estos procesos:

* Se creará una lista en orden inverso con los hermanos a partir del segundo sin incluir a este.
* Si el primero nodo (es decir el de la variante principal) continua (tiene hijos: `if(hasChildren(node))`) se guardará en una pila para volver a el posteriormente.
* Se anotará el movimiento en formato pgn con una serie de condiciones.
* Y se añadirán los elementos de la lista creada a la pila, por encima de la variante principal.

Sí, por el contrario, no se cumplen estas condiciones, en `else` se ecnontrá con esta condición: `if(isNotInHistoryOrFromStack(id, comesFromStack, historyStack))` la cual se encarga de filtrar los duplicados que se crean por la pila, para que estos no entren aquí (y arriba tampoco entrar ya que cuando vienen de la pila se pasa como una lista de un solo elemento).
Tras una serie de comprobaciones se anotará el movimiento en el PGN.
Y llegamos a la parte de la recursividad si el nodo tiene hijos mandamos a sus hijos a `subVariants(node.childrenIds, false)` marcando que NO vienen de la pila.
Si el nodo no tiene hijos quiere decir que esa variante (sea principal o secundaria) ha terminado.
Si esa variante ha terminado comprobamos si es el último elemento de la lista (`if(isLastElement(index, movesList))`), ya aquí hay 3 caminos: 
* Si es el último elemento y todavía hay elementos en la pila, sacamos el último y lo mandamos `subVariants(mutableListOf(last), true)`, marcando true ya que viene de la pila.
* Si es el último elemento, pero ya no quedan elemento en la pila, hemos terminado y añadimos los paréntesis que sean necesarios para terminar la variante.
* Si no era el último elemento, pero es el segundo y la pila no está vacía mandamos el último elemento de la pila: `subVariants(mutableListOf(last), true)`. Esto último se debe a que como la variante ha terminado se debe respetar el orden en el que las variantes se escriben en un PGN.

Ahora veamos un ejemplo práctico de como funciona este algoritmo en un PGN real:

<div align="center">
  <img src="/assets/ejemplo_pgn.png" />
</div>

En la primera iteración se ha mandado una lista con solamente e4 la primera condición (`index == 0 && movesList.size > 1`) no la pasa, entra en el else, luego entra en el if, ya que no e4 no viene de la pila. 
Como se cumple que e4 tiene hijos (e5, e6 y d5) la función se llama recursivamente mandándole una lista con los hijos de e4 y marcando que no se ha extraído de la pila.

Volvemos a partir del for, esta vez con e5 y sí que entra, ya que `lista.size > 1` y como e5 tiene hijos (d4) entra a la pila y luego con la sublista, d5 también, por ende tenemos ahora en la pila: (e5, d5).
Vamos a la segunda iteración de los hijos de e4 (vamos con e6), e6 acabo entrando en este bloque: 
```
  if(hasChildren(node)){
     subVariants(node.childrenIds, false)
  }
```

Y manda Cf3 (su hijo) y tras seguir un poco el algoritmo vemos que como no tiene hijos no se cumple el if que hemos visto arriba y sí que se cumple esto: `if(isLastElement(index, movesList))` y además la pila no está vacía, por lo tanto, sacamos el último elemento: d5.

Volvemos a la primera condicón, no se cumple ya que se ha enviado d5 en una lista donde d5 es el único elemento. Y la condición `if(isNotInHistoryOrFromStack(id, comesFromStack, historyStack))` o mejor dicho: 
```
    private fun isNotInHistoryOrFromStack(id: String, comesFromStack: Boolean, historyStack: MutableMap<String, Boolean>): Boolean {
        return !(historyStack.contains(id) && !comesFromStack)
    }
```
Si que se cumple ya que si que está en historyStack d5 y viene de stack, por ende: !(true && !true) -> !(true && false) -> !(false) -> true.

Una vez dentro y como tiene hijos (a4 y Cc3) manda a estos en una lista a la función de nuevo.

Repetimos el proceso y a4 acaba en la pila, pasamos a Cc3, este llama a la pila, sacamos a4, continuamos con a5, este acaba llamando a la pila y sacamos el último, e5, y terminamos con d4.

Esto tan solo es el algoritmo que se encarga de ordenar los nodos por el orden en el que se llaman, esto, combinado con unas rigurosas comprobaciones para añadir paréntesis, índices y más daría como resultado esto: 
`1. e4 e5 (1... e6 2. Nf3) (1... d5 2. a4 (2. Nc3) 2... a5) 2. d4`

* **Nota importante** las variantes principales de cualquier línea (tanto en variantes, como en la principal) se guardan historyStack como false, ya que estas se anotan ahí en el pgn para que luego no se anoten al sacarlos de la pila y continuar con esa línea que se interrumpió por una variante. Esto aquí se comprueba: `if(historyStack[id] == true)` y si es false quiere decir que es una principal (que se anotó ya en el momento en el que se guardó en la pila) y no debe de ser anotada ahí, simplemente continuará su camino por la función.
* Nota: La notación de la imagen está en español Caballo(Cc3) y la del resultado en PGN en inglés Knight(Nc3).

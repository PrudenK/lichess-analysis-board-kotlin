### MovesPanel.kt

`MovesPanel` es el componente dedicado a mostrar la notación PGN en un panel de movimientos con distintas variantes, igual que en Lichess.

Respecto a las variantes partiendo de la principal la lógica ha sido resuelta desglosándolo en 5 escenarios posibles, sean CASO 0...4

* CASO 0 -> No hay ningún tipo de variante.
  * Ejemplo: `1. e4 e5 2. Nf3 Nc6`
* CASO 1 -> El negro es quien tiene hijos, usamos `WhiteSiblingVariantsUnderBlack(parentOfWhite: MoveNode?, hasWhiteSiblings: Boolean)` y posteriormente dibujamos con `MainLineRowComponentForCase1` el salto de los ... indicando la variante en si. 
  * Ejemplo: `1. e4 e5 2. Nf3 (2. Nc3) (2. Bc4) 2... Nc6`
* CASO 2 -> El blanco tiene hijos, usamos `WhiteChildrenVariants(white: MoveNode?)`.
  * Ejemplo: `1. e4 e5 (1... e6 2. d4) 2. Nf3`
* CASO 3 -> El padre es root, para ello de la siguiente condición `(parentOfWhite?.isWhiteMove == false || parentOfWhite?.id == "root")` en `VariantHelper.getIfhasWhiteSiblings(white: MoveNode?, parentOfWhite: MoveNode?): Boolean`.
  * Ejemplo: `1. e4 (1. d4 Nf6) 1... e5`
* CASO 4 -> Tanto el blanco como el negro tienen hijos, es por ello que `WhiteChildrenVariants(white)` está al final de la Column.
  * Ejemplo: `1. e4 e5 (1... e6) 2. Nf3 (2. Nc3) 2... d6 (2... c6)`


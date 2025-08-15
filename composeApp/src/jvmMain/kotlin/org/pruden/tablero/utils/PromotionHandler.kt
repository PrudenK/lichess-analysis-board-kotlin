package org.pruden.tablero.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.pruden.tablero.globals.Globals
import org.pruden.tablero.models.Piece
import org.pruden.tablero.models.PieceType

object PromotionHandler {
    @Composable
    fun applyPromotionFilter(){
        if (Globals.isWhitePromotion.value || Globals.isBlackPromotion.value) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }
    }

    fun verifyPromotion(
        movedPiece: Piece
    ){
        if(movedPiece.type == PieceType.Pawn){
            if(movedPiece.color == org.pruden.tablero.models.Color.White){
                if(movedPiece.positionToChessNotation().contains("8")){
                    Globals.promotionCol = movedPiece.position.first
                    Globals.isWhitePromotion.value = true

                }
            }else{
                if(movedPiece.positionToChessNotation().contains("1")){
                    Globals.promotionCol = movedPiece.position.first
                    Globals.isBlackPromotion.value = true
                }
            }
        }
    }
}
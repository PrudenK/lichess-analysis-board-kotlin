package org.pruden.tablero.api.constants

import org.pruden.tablero.api.services.ModuleService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiChess {
    private const val BASE_URL = "https://chess-api.com/"

    const val V1 = "v1"


    private val retrofitChess = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moduleService : ModuleService = retrofitChess.create(ModuleService::class.java)
}
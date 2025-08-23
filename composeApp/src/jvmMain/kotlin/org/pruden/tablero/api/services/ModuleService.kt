package org.pruden.tablero.api.services

import org.pruden.tablero.api.constants.ApiChess
import org.pruden.tablero.api.objects.request.EvalRequest
import org.pruden.tablero.api.objects.response.EvalResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ModuleService {

    @POST(ApiChess.V1)
    suspend fun evaluatePosition(@Body body: EvalRequest): EvalResponse
}
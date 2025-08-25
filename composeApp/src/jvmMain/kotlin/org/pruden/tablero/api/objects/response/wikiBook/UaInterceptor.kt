package org.pruden.tablero.api.objects.response.wikiBook

import okhttp3.Interceptor
import okhttp3.Response

class UaInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder()
            .header("User-Agent", "TableroAjedrez/1.0 (contacto: tu-email@dominio.com)")
            .header("Accept", "application/json")
            .build()
        return chain.proceed(req)
    }
}
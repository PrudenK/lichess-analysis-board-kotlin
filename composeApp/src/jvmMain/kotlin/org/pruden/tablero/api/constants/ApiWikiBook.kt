package org.pruden.tablero.api.constants

import okhttp3.OkHttpClient
import org.pruden.tablero.api.objects.response.wikiBook.UaInterceptor
import org.pruden.tablero.api.services.WikiBookService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiWikiBook {
    private val BASE_URL = "https://en.wikibooks.org/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(UaInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofiWikiBook = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val wikiBookService: WikiBookService = retrofiWikiBook.create(WikiBookService::class.java)
}
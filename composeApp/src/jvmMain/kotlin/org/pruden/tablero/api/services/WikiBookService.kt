package org.pruden.tablero.api.services

import org.pruden.tablero.api.objects.response.wikiBook.WikiBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiBookService {
    @GET("w/api.php")
    suspend fun getExtract(
        @Query(value = "titles", encoded = true) titles: String,
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "extracts",
        @Query("format") format: String = "json",
        @Query("redirects") redirects: Int = 1
    ): WikiBookResponse

}
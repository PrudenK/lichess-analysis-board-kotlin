package org.pruden.tablero.api.objects.response.wikiBook

data class WikiBookQuery(
    val pages: Map<String, WikiBookPage> = emptyMap()
)

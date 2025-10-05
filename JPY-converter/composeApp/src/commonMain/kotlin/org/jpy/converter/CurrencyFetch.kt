package org.jpy.converter

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class CurrencyFetch {
    private val client = HttpClient()

    suspend fun getCurrencies(): String {
        val response = client.get("https://jpy-conversion-functions.partavesipirtelo.workers.dev/")
        return response.bodyAsText()
    }
}
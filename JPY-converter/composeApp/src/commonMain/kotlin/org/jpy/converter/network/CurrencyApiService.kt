package org.jpy.converter.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.jpy.converter.model.Currencies
import org.jpy.converter.model.parseCurrenciesJson

class CurrencyApiService {
    private val client = HttpClient()

    suspend fun getCurrencies(): Currencies {
        val response = client.get("https://jpy-conversion-functions.partavesipirtelo.workers.dev/")
        val parsed = parseCurrenciesJson(response.bodyAsText())
        return parsed
    }
}
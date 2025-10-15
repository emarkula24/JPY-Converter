package org.jpy.converter.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class CurrencyQuote(
    val date: String,
    val baseCurrency: String,
    val quoteCurrency: String,
    val quote: Double
)

@Serializable
data class CurrencyData(
    val latest: List<CurrencyQuote>
)

@Serializable
data class Currencies(val data: CurrencyData)

fun parseCurrenciesJson(json: String): Currencies = Json.decodeFromString(json)

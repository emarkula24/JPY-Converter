package org.jpy.converter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.jpy.converter.model.CurrencyQuote
import org.jpy.converter.util.formatDouble

class ConversionViewModel : ViewModel() {

    var fromCurrency by mutableStateOf("")
        private set

    var toCurrency by mutableStateOf("")
        private set
    var currencyAmount: String by mutableStateOf("")
        private set

    var conversionResult: String by mutableStateOf("")
        private set
    var currencyRates: List<CurrencyQuote> = emptyList()
        private set
    var rate: String by mutableStateOf("")
    var jpyMultiplier: Int = 0
        private set

    fun setJpyMultiplier(multiplier: Int) {
        jpyMultiplier = multiplier
        updateConversion()
    }
    fun setRates(rates: List<CurrencyQuote>) {
        currencyRates = rates
        updateConversion()
    }
    fun onFromCurrencySelected(currency: String) {
        fromCurrency = currency
        updateConversion()
    }

    fun onToCurrencySelected(currency: String) {
        toCurrency = currency
        updateConversion()
    }
    fun onAmountChanged(amount: String) {
        currencyAmount = amount
        updateConversion()
    }

    fun swapCurrencies() {
        val temp = fromCurrency
        fromCurrency = toCurrency
        toCurrency = temp
        currencyAmount = ""
        conversionResult = ""
        rate = ""

        updateConversion()
    }

    fun getCurrencySymbol(code: String): String {
        val symbols = mapOf(
            "AUD" to "A$",
            "CNY" to "¥",
            "EUR" to "€",
            "GBP" to "£",
            "JPY" to "円",
            "NZD" to "NZ$",
            "THB" to "฿",
            "TWD" to "NT$",
            "USD" to "$"
        )
        return symbols[code] ?: code
    }

    private fun updateConversion() {
        val fromQuote = currencyRates.find { it.quoteCurrency == fromCurrency }
        val toQuote = currencyRates.find { it.quoteCurrency == toCurrency }
        val amount = currencyAmount.trim().toDoubleOrNull() ?: return

        if (fromQuote != null && toQuote != null) {
            val adjustedAmount = if (fromCurrency == "JPY") {
                amount * jpyMultiplier
            } else {
                amount
            }
            val amountInEUR = if (fromCurrency == "EUR") {
                adjustedAmount
            } else {
                adjustedAmount / fromQuote.quote
            }
            val convertedAmount = if (toCurrency == "EUR") {
                amountInEUR
            } else {
                amountInEUR * toQuote.quote
            }

            conversionResult = formatDouble(convertedAmount)

            val effectiveRate = when {
                fromCurrency == "EUR" -> toQuote.quote
                toCurrency == "EUR" -> 1 / fromQuote.quote
                else -> toQuote.quote / fromQuote.quote
            }
            rate = formatDouble(effectiveRate, 6)
        } else {
            conversionResult = ""
            rate = ""
        }

    }
}
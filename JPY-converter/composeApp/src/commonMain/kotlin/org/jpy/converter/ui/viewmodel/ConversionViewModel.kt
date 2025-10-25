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
    var rate: String by mutableStateOf("0")

    var isManualRates: Boolean by mutableStateOf(false)
        private set
    var manualRate by mutableStateOf("")
        private set
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
        val maxDigits = 9
        if (amount.length <= maxDigits && amount.all { it.isDigit() }) {
            currencyAmount = amount
            updateConversion()
        }
    }

    fun setIsManualRate(enabled: Boolean) {
        isManualRates = enabled
        if (!enabled) {
            manualRate = ""
        }
        updateConversion()
    }
    fun onManualRateChanged(rate: String) {
        manualRate = rate
        updateConversion()
    }
    fun swapCurrencies() {
        val temp = fromCurrency
        fromCurrency = toCurrency
        toCurrency = temp
        currencyAmount = ""
        conversionResult = ""
        rate = "0"

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
        updateRate()

        val amount = currencyAmount.trim().toDoubleOrNull() ?: return
        val adjustedAmount = if (fromCurrency == "JPY") amount * jpyMultiplier else amount

        val convertedAmount: Double = if (isManualRates) {
            val effectiveRate = manualRate.toDoubleOrNull() ?: return
            adjustedAmount * effectiveRate
        } else {
            val fromQuote = currencyRates.find { it.quoteCurrency == fromCurrency }
            val toQuote = currencyRates.find { it.quoteCurrency == toCurrency }
            if (fromQuote == null || toQuote == null) return

            val amountInEUR = if (fromCurrency == "EUR") {
                adjustedAmount
            } else {
                adjustedAmount / fromQuote.quote
            }

            if (toCurrency == "EUR") {
                amountInEUR
            } else {
                amountInEUR * toQuote.quote
            }
        }

        conversionResult = formatDouble(convertedAmount)
    }
    

    private fun updateRate() {
        val effectiveRate: Double = if (isManualRates) {
            manualRate.toDoubleOrNull() ?: return
        } else {
            val fromQuote = currencyRates.find { it.quoteCurrency == fromCurrency }
            val toQuote = currencyRates.find { it.quoteCurrency == toCurrency }
            if (fromQuote == null || toQuote == null) return

            when {
                fromCurrency == "EUR" -> toQuote.quote
                toCurrency == "EUR" -> 1 / fromQuote.quote
                else -> toQuote.quote / fromQuote.quote
            }
        }

        rate = formatDouble(effectiveRate, 4)
    }

}
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
    var currencyAmount: String by mutableStateOf("0")
        private set

    var conversionResult: String by mutableStateOf("")
        private set
    var currencyRates: List<CurrencyQuote> = emptyList()
        private set

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
        updateConversion()
    }
    private fun updateConversion() {
        val fromQuote = currencyRates.find { it.quoteCurrency == fromCurrency }
        val toQuote = currencyRates.find { it.quoteCurrency == toCurrency }
        val amount = currencyAmount.toDoubleOrNull() ?: return

        if (fromQuote != null && toQuote != null) {
            val amountInEUR = if (fromCurrency == "EUR") {
                amount
            } else {
                amount / fromQuote.quote
            }
            val convertedAmount = if (toCurrency == "EUR") {
                amountInEUR
            } else {
                amountInEUR * toQuote.quote
            }
            conversionResult = formatDouble(convertedAmount)
        } else {
            conversionResult = ""
        }

    }
}
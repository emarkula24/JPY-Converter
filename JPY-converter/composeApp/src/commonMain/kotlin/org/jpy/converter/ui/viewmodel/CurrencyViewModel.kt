package org.jpy.converter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jpy.converter.model.Currencies
import org.jpy.converter.model.CurrencyQuote
import org.jpy.converter.network.CurrencyApiService
import org.jpy.converter.util.formatDouble

sealed interface CurrencyUiState {
    data class Success(val currencies: Currencies) : CurrencyUiState
    object Error : CurrencyUiState
    object Loading : CurrencyUiState
}

class CurrencyViewModel: ViewModel() {
    var currencyUiState: CurrencyUiState by mutableStateOf(CurrencyUiState.Loading)
        private set

    var fromCurrency by mutableStateOf("")
        private set

    var toCurrency by mutableStateOf("")
        private set

    var conversionResult: String by mutableStateOf("")
        private set
    var currencyRates: List<CurrencyQuote> = emptyList()
        private set
    var currencyAmount: String by mutableStateOf("0")
        private set
    private val apiService = CurrencyApiService()
    init {
        getCurrencies()
    }

    fun getCurrencies() {
        viewModelScope.launch {
            currencyUiState = CurrencyUiState.Loading
            try {
                val listResult = apiService.getCurrencies()

                currencyUiState = CurrencyUiState.Success(listResult)
                val available = listResult.data.latest.map { it.quoteCurrency }
                fromCurrency = available.find { it == "JPY" } ?: available.firstOrNull().orEmpty()
                toCurrency = available.find { it == "EUR" } ?: available.getOrNull(1).orEmpty()
                currencyRates = listResult.data.latest
                updateConversion()

            } catch (e: Exception) {
                currencyUiState = CurrencyUiState.Error
            }
        }
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



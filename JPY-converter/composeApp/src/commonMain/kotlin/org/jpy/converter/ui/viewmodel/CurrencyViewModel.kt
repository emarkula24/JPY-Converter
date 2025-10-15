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

sealed interface CurrencyUiState {
    data class Success(val currencies: Currencies) : CurrencyUiState
    object Error : CurrencyUiState
    object Loading : CurrencyUiState
}

class CurrencyViewModel: ViewModel() {
    var currencyUiState: CurrencyUiState by mutableStateOf(CurrencyUiState.Loading)
        private set
    private val apiService = CurrencyApiService()

    fun getCurrencies(onRatesReady: (List<CurrencyQuote>, List<String>) -> Unit) {
        viewModelScope.launch {
            currencyUiState = CurrencyUiState.Loading
            try {
                val listResult = apiService.getCurrencies()
                currencyUiState = CurrencyUiState.Success(listResult)

                val available = listResult.data.latest.map { it.quoteCurrency }
                onRatesReady(listResult.data.latest, available)

            } catch (e: Exception) {
                currencyUiState = CurrencyUiState.Error
            }
        }

    }
}



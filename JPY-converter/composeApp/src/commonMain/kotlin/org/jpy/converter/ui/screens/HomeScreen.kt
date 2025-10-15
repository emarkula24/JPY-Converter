package org.jpy.converter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jpy.converter.model.Currencies
import org.jpy.converter.ui.components.LabeledCurrencyDropdown
import org.jpy.converter.ui.components.NumberInputField
import org.jpy.converter.ui.viewmodel.CurrencyUiState
import org.jpy.converter.ui.viewmodel.CurrencyViewModel


@Composable
fun HomeScreen(
    viewModel: CurrencyViewModel,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    when (val state = viewModel.currencyUiState) {
        is CurrencyUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CurrencyUiState.Success -> ResultScreen(
             state.currencies, modifier.fillMaxWidth(), viewModel
        )
        is CurrencyUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            modifier = modifier.width(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Something went wrong")
    }
}

@Composable
fun ResultScreen(
    currencies: Currencies,
    modifier: Modifier = Modifier,
    viewModel: CurrencyViewModel,
) {
    val quoteCurrencies = currencies.data.latest.map { it.quoteCurrency }

    Column {
        LabeledCurrencyDropdown(
            label = "From:",
            selectedCurrency = viewModel.fromCurrency,
            currencyOptions = quoteCurrencies,
            onCurrencySelected = viewModel::onFromCurrencySelected,
        )

        LabeledCurrencyDropdown(
            label = "To:",
            selectedCurrency = viewModel.toCurrency,
            currencyOptions = quoteCurrencies,
            onCurrencySelected = viewModel::onToCurrencySelected,
        )
        Spacer(modifier = Modifier.height(16.dp))
        NumberInputField(onAmountChanged = viewModel::onAmountChanged)
        Text(viewModel.conversionResult)
    }
}

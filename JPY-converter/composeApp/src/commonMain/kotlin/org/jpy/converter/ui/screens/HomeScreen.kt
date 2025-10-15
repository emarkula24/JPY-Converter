package org.jpy.converter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jpy.converter.model.Currencies
import org.jpy.converter.ui.components.JpyConversionOutputElement
import org.jpy.converter.ui.components.JpyMultiplierSelectElement
import org.jpy.converter.ui.components.LabeledCurrencyDropdown
import org.jpy.converter.ui.components.NumberInputField
import org.jpy.converter.ui.viewmodel.ConversionViewModel
import org.jpy.converter.ui.viewmodel.CurrencyUiState
import org.jpy.converter.ui.viewmodel.CurrencyViewModel
import org.jpy.converter.ui.viewmodel.JpyViewModel


@Composable
fun HomeScreen(
    currencyViewModel: CurrencyViewModel,
    conversionViewModel: ConversionViewModel,
    jpyViewModel: JpyViewModel,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    LaunchedEffect(Unit) {
        currencyViewModel.getCurrencies { rates, available ->
            conversionViewModel.setRates(rates)
            conversionViewModel.onFromCurrencySelected(available.find { it == "JPY" } ?: available.first())
            conversionViewModel.onToCurrencySelected(available.find { it == "EUR" } ?: available.getOrNull(1).orEmpty())
        }
    }
    when (val state = currencyViewModel.currencyUiState) {
        is CurrencyUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CurrencyUiState.Success -> ResultScreen(
            state.currencies, modifier.fillMaxWidth(), conversionViewModel, jpyViewModel
        )

        is CurrencyUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            modifier = modifier.width(2.dp),
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
    conversionViewModel: ConversionViewModel,
    jpyViewModel: JpyViewModel,

) {
    val quoteCurrencies = currencies.data.latest.map { it.quoteCurrency }

    Column {
        LabeledCurrencyDropdown(
            label = "From:",
            selectedCurrency = conversionViewModel.fromCurrency,
            currencyOptions = quoteCurrencies,
            onCurrencySelected = conversionViewModel::onFromCurrencySelected,
        )
        Button(onClick = { conversionViewModel.swapCurrencies()}) {
            Text("Swap")
        }
        LabeledCurrencyDropdown(
            label = "To:",
            selectedCurrency = conversionViewModel.toCurrency,
            currencyOptions = quoteCurrencies,
            onCurrencySelected = conversionViewModel::onToCurrencySelected,
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (conversionViewModel.fromCurrency == "JPY") {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                JpyMultiplierSelectElement(
                    label = "百",
                    intValue = 100,
                    selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                    onMultiplierSelected = { label, value ->
                        jpyViewModel.onMultiplierSelected(label, value)
                        conversionViewModel.setJpyMultiplier(value)
                    }
                )
                JpyMultiplierSelectElement(
                    label = "千",
                    intValue = 1000,
                    selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                    onMultiplierSelected = { label, value ->
                        jpyViewModel.onMultiplierSelected(label, value)
                        conversionViewModel.setJpyMultiplier(value)
                    }
                )
                JpyMultiplierSelectElement(
                    label = "万",
                    intValue = 10000,
                    selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                    onMultiplierSelected = { label, value ->
                        jpyViewModel.onMultiplierSelected(label, value)
                        conversionViewModel.setJpyMultiplier(value)
                    }
                )
                JpyMultiplierSelectElement(
                    label = "億",
                    intValue = 100000000,
                    selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                    onMultiplierSelected = { label, value ->
                        jpyViewModel.onMultiplierSelected(label, value)
                        conversionViewModel.setJpyMultiplier(value)
                    }
                )
            }
        }
        NumberInputField(
            amount = conversionViewModel.currencyAmount,
            onAmountChanged = conversionViewModel::onAmountChanged)
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (conversionViewModel.currencyAmount.isNotBlank()) {
                if (conversionViewModel.toCurrency == "JPY") {
                    JpyConversionOutputElement(
                        jpyViewModel = jpyViewModel,
                        conversionResult = conversionViewModel.conversionResult
                    )
                    Text(text = "" + conversionViewModel.getCurrencySymbol(conversionViewModel.toCurrency))

                } else {
                    Text(conversionViewModel.conversionResult)
                    Text(text = "" + conversionViewModel.getCurrencySymbol(conversionViewModel.toCurrency))
                }

            }


        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "today's exchange rate is: ${conversionViewModel.rate}")
    }
}

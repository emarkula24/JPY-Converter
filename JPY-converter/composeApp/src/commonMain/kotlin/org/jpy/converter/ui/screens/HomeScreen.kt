package org.jpy.converter.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jpyconverter.composeapp.generated.resources.Res
import jpyconverter.composeapp.generated.resources.error_message
import jpyconverter.composeapp.generated.resources.intro_message
import jpyconverter.composeapp.generated.resources.label_from
import jpyconverter.composeapp.generated.resources.label_ichi
import jpyconverter.composeapp.generated.resources.label_man
import jpyconverter.composeapp.generated.resources.label_manualrate
import jpyconverter.composeapp.generated.resources.label_oku
import jpyconverter.composeapp.generated.resources.label_sen
import jpyconverter.composeapp.generated.resources.label_swap
import jpyconverter.composeapp.generated.resources.rate_message
import jpyconverter.composeapp.generated.resources.rate_message2
import org.jetbrains.compose.resources.stringResource
import org.jpy.converter.model.Currencies
import org.jpy.converter.themes.LocalColors
import org.jpy.converter.ui.components.JpyConversionOutputElement
import org.jpy.converter.ui.components.JpyMultiplierSelectElement
import org.jpy.converter.ui.components.LabeledCurrencyDropdown
import org.jpy.converter.ui.components.ManualExchangeRateCheckbox
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
) {
    LaunchedEffect(Unit) {
        currencyViewModel.getCurrencies { rates, available ->
            conversionViewModel.setRates(rates)
            conversionViewModel.onFromCurrencySelected(available.find { it == "JPY" } ?: available.first())
            conversionViewModel.onToCurrencySelected(available.find { it == "EUR" } ?: available.getOrNull(1).orEmpty())
        }
    }
    when (val state = currencyViewModel.currencyUiState) {
        is CurrencyUiState.Loading -> LoadingScreen()
        is CurrencyUiState.Success -> ResultScreen(
            state.currencies, modifier.fillMaxWidth(), conversionViewModel, jpyViewModel
        )

        is CurrencyUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}


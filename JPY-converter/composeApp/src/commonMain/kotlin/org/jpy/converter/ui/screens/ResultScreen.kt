package org.jpy.converter.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jpyconverter.composeapp.generated.resources.Res
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
import org.jpy.converter.themes.ConverterTheme
import org.jpy.converter.themes.LocalColors
import org.jpy.converter.ui.components.JpyConversionOutputElement
import org.jpy.converter.ui.components.JpyMultiplierSelectElement
import org.jpy.converter.ui.components.LabeledCurrencyDropdown
import org.jpy.converter.ui.components.ManualExchangeRateCheckbox
import org.jpy.converter.ui.components.NumberInputField
import org.jpy.converter.ui.viewmodel.ConversionViewModel
import org.jpy.converter.ui.viewmodel.JpyViewModel

@Composable
fun ResultScreen(
    currencies: Currencies,
    modifier: Modifier = Modifier,
    conversionViewModel: ConversionViewModel,
    jpyViewModel: JpyViewModel,
) {
    val colors = LocalColors.current
    val quoteCurrencies = currencies.data.latest.map { it.quoteCurrency }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() }
                )
                                },
        color = colors.background,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {
            Spacer(modifier = Modifier.height(64.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = stringResource(Res.string.intro_message),
                    modifier = Modifier.widthIn(max = 300.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

            }


            LabeledCurrencyDropdown(
                label = stringResource(Res.string.label_from),
                selectedCurrency = conversionViewModel.fromCurrency,
                currencyOptions = quoteCurrencies,
                onCurrencySelected = conversionViewModel::onFromCurrencySelected,
            )

            OutlinedButton(
                onClick = { conversionViewModel.swapCurrencies() },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalColors.current.background,
                    contentColor = LocalColors.current.primary,
                    disabledContainerColor = LocalColors.current.background,
                    disabledContentColor = LocalColors.current.primary
                ),
                border = BorderStroke(1.dp, LocalColors.current.primary),

                ) {
                Text(stringResource(Res.string.label_swap), style = ConverterTheme.typo.bodyMedium,)
            }
            LabeledCurrencyDropdown(
                label = stringResource(Res.string.label_from),
                selectedCurrency = conversionViewModel.toCurrency,
                currencyOptions = quoteCurrencies,
                onCurrencySelected = conversionViewModel::onToCurrencySelected,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (conversionViewModel.fromCurrency == "JPY") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    JpyMultiplierSelectElement(
                        label = stringResource(Res.string.label_ichi),
                        intValue = 1,
                        selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                        onMultiplierSelected = { label, value ->
                            jpyViewModel.onMultiplierSelected(label, value)
                            conversionViewModel.setJpyMultiplier(value)
                        }
                    )
                    JpyMultiplierSelectElement(
                        label = stringResource(Res.string.label_sen),
                        intValue = 1000,
                        selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                        onMultiplierSelected = { label, value ->
                            jpyViewModel.onMultiplierSelected(label, value)
                            conversionViewModel.setJpyMultiplier(value)
                        }
                    )
                    JpyMultiplierSelectElement(
                        label = stringResource(Res.string.label_man),
                        intValue = 10000,
                        selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                        onMultiplierSelected = { label, value ->
                            jpyViewModel.onMultiplierSelected(label, value)
                            conversionViewModel.setJpyMultiplier(value)
                        }
                    )
                    JpyMultiplierSelectElement(
                        label = stringResource(Res.string.label_oku),
                        intValue = 100000000,
                        selectedMultiplier = jpyViewModel.selectedJpyMultiplier,
                        onMultiplierSelected = { label, value ->
                            jpyViewModel.onMultiplierSelected(label, value)
                            conversionViewModel.setJpyMultiplier(value)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            NumberInputField(viewModel = conversionViewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (conversionViewModel.currencyAmount.isNotBlank()) {
                    if (conversionViewModel.toCurrency == "JPY") {
                        JpyConversionOutputElement(
                            jpyViewModel = jpyViewModel,
                            conversionResult = conversionViewModel.conversionResult
                        )
                        Text(
                            text = "" + conversionViewModel.getCurrencySymbol(conversionViewModel.toCurrency), style = ConverterTheme.typo.bodyMedium
                        )

                    } else {
                        Text(conversionViewModel.conversionResult, style = ConverterTheme.typo.bodyMedium,)
                        Text(
                            text = "" + conversionViewModel.getCurrencySymbol(conversionViewModel.toCurrency), style = ConverterTheme.typo.bodyMedium
                        )
                    }

                }


            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.rate_message) + conversionViewModel.rate +
                        stringResource(Res.string.rate_message2),
                style = ConverterTheme.typo.bodyMedium,
            )

            ManualExchangeRateCheckbox(conversionViewModel)

            if (conversionViewModel.isManualRates) {
                OutlinedTextField(
                    value = conversionViewModel.manualRate,
                    onValueChange = conversionViewModel::onManualRateChanged,
                    label = {
                        Text(stringResource(Res.string.label_manualrate), style = ConverterTheme.typo.bodyMedium,)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = LocalColors.current.primary,
                        unfocusedLabelColor = LocalColors.current.primary,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = LocalColors.current.primary,
                        unfocusedBorderColor = LocalColors.current.primary,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
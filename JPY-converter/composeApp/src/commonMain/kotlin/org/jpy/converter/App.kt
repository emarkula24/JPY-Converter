package org.jpy.converter

import androidx.compose.runtime.Composable
import org.jpy.converter.ui.viewmodel.CurrencyViewModel
import org.jpy.converter.ui.screens.HomeScreen
import org.jpy.converter.ui.viewmodel.ConversionViewModel

@Composable
fun App(
    currencyViewModel: CurrencyViewModel = CurrencyViewModel(),
    conversionViewModel: ConversionViewModel = ConversionViewModel(),
) {
    HomeScreen(
        currencyViewModel = currencyViewModel,
        conversionViewModel = conversionViewModel,
    )
}
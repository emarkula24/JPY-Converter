package org.jpy.converter

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jpy.converter.ui.viewmodel.CurrencyViewModel
import org.jpy.converter.ui.screens.HomeScreen
import org.jpy.converter.ui.viewmodel.ConversionViewModel
import org.jpy.converter.ui.viewmodel.JpyViewModel

@Composable
fun App(
    currencyViewModel: CurrencyViewModel = CurrencyViewModel(),
    conversionViewModel: ConversionViewModel = ConversionViewModel(),
    jpyViewModel: JpyViewModel = JpyViewModel(),
) {
    HomeScreen(
        currencyViewModel = currencyViewModel,
        conversionViewModel = conversionViewModel,
        jpyViewModel = jpyViewModel,
    )
}
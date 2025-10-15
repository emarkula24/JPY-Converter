package org.jpy.converter

import androidx.compose.runtime.Composable
import org.jpy.converter.ui.viewmodel.CurrencyViewModel
import org.jpy.converter.ui.screens.HomeScreen

@Composable
fun App(viewModel: CurrencyViewModel = CurrencyViewModel()) {
    HomeScreen(viewModel = viewModel)
}
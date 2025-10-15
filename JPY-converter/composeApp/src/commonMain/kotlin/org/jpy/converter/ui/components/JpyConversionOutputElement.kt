package org.jpy.converter.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.jpy.converter.ui.viewmodel.JpyViewModel
import org.jpy.converter.util.formatDouble


@Composable
fun JpyConversionOutputElement(
    jpyViewModel: JpyViewModel,
    conversionResult: String,
) {
    LaunchedEffect(conversionResult) {
        jpyViewModel.updateFormattedJpyOutput(conversionResult)
    }
    Text(text = jpyViewModel.formattedJpyOutput)
}
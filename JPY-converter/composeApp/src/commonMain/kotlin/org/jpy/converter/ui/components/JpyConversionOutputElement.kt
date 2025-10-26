package org.jpy.converter.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import org.jpy.converter.themes.ConverterTheme
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
    Text(
        text = jpyViewModel.formattedJpyOutput,
        style = ConverterTheme.typo.bodyMedium,
    )
}
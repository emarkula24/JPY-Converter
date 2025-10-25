package org.jpy.converter.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import org.jpy.converter.ui.viewmodel.ConversionViewModel

@Composable
fun NumberInputField(viewModel: ConversionViewModel) {
    TextField(
        value = viewModel.currencyAmount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text("Amount") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}


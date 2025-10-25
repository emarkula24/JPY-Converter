package org.jpy.converter.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import jpyconverter.composeapp.generated.resources.Res
import jpyconverter.composeapp.generated.resources.currency_amount
import org.jetbrains.compose.resources.stringResource
import org.jpy.converter.ui.viewmodel.ConversionViewModel

@Composable
fun NumberInputField(viewModel: ConversionViewModel) {
    OutlinedTextField(
        value = viewModel.currencyAmount,
        onValueChange = viewModel::onAmountChanged,
        label = { Text(stringResource(Res.string.currency_amount)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}


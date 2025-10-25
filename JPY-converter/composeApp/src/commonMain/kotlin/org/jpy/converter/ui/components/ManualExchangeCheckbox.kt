package org.jpy.converter.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jpy.converter.ui.viewmodel.ConversionViewModel

@Composable
fun ManualExchangeRateCheckbox(conversionViewModel: ConversionViewModel) {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Checkbox(
            checked = conversionViewModel.isManualRates,
            onCheckedChange = { conversionViewModel.setIsManualRate(it) }
        )
        Text(
            text = "Manual exchange rate",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
package org.jpy.converter.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun JpyMultiplierSelectElement(
    label: String,
    intValue: Int,
    selectedMultiplier: String,
    onMultiplierSelected: (String, Int) -> Unit,
) {
    val isSelected = selectedMultiplier == label
    val backgroundColor = if (isSelected) Color(0xFF1976D2) else Color(0xFFE0E0E0)
    val contentColor = if (isSelected) Color.White else Color.Black
    ElevatedButton(
        onClick = { onMultiplierSelected(label, intValue) },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier.padding(4.dp)
        ) {
        Text(label)
    }
}
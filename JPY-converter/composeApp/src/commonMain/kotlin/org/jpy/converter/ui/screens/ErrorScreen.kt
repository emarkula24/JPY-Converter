package org.jpy.converter.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import jpyconverter.composeapp.generated.resources.Res
import jpyconverter.composeapp.generated.resources.error_message
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.error_message),
            color = MaterialTheme.colorScheme.error,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}
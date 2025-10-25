package org.jpy.converter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jpy.converter.ui.viewmodel.CurrencyViewModel
import org.jpy.converter.ui.screens.HomeScreen
import org.jpy.converter.ui.viewmodel.ConversionViewModel
import org.jpy.converter.ui.viewmodel.JpyViewModel


var customAppThemeIsDark by mutableStateOf<Boolean?>(null)
expect object LocalAppTheme {
    val current: Boolean @Composable get
    @Composable infix fun provides(value: Boolean?): ProvidedValue<*>
}

@Composable
fun AppEnvironment(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppTheme provides customAppThemeIsDark,
    ) {
        key(customAppThemeIsDark) {
            content()
        }
    }
}
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
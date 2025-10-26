package org.jpy.converter.themes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.jpy.converter.LocalAppTheme


val LightJpyColors = Colors(
    primary = Color(0xFF56bfd6),
    onPrimary = Color.White,
    background = Color(0xFFf3efef),
    onBackground = Color.Black,
    tertiary = Color(0xFFE0E0E0)
    )
val DarkJpyColors = Colors(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    tertiary = Color(0xFFE0E0E0))

val LocalColors = staticCompositionLocalOf { LightJpyColors }

@Composable
fun ConverterTheme(
    darkTheme: Boolean = LocalAppTheme.current,
    content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkJpyColors else LightJpyColors

    CompositionLocalProvider(LocalColors provides colors) {
        content()
    }
}
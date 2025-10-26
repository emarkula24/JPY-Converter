package org.jpy.converter.themes

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import jpyconverter.composeapp.generated.resources.NotoSansJP_VariableFont_wght
import jpyconverter.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
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
val LocalTypography = staticCompositionLocalOf { Typography() }


@Composable
@OptIn(ExperimentalResourceApi::class)
fun rememberJpyFontFamily(): FontFamily {
    val font = Font(Res.font.NotoSansJP_VariableFont_wght, weight = FontWeight.Normal)
    return FontFamily(font)
}


object ConverterTheme {
    val colors: Colors
        @Composable get() = LocalColors.current

    val typo: Typography
        @Composable get() = LocalTypography.current
}

@Composable
fun ConverterTheme(
    darkTheme: Boolean = LocalAppTheme.current,
    content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkJpyColors else LightJpyColors


    val typography = Typography(
        bodyLarge = TextStyle(fontFamily = rememberJpyFontFamily()),
        bodyMedium = TextStyle(fontFamily = rememberJpyFontFamily()),
        titleLarge = TextStyle(fontFamily = rememberJpyFontFamily())
        // Add more styles as needed
    )

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography
    )
    {
        content()
    }
}
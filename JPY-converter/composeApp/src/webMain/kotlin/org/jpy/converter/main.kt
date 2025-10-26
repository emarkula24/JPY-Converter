package org.jpy.converter

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.LocalSystemTheme
import androidx.compose.ui.SystemTheme
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import jpyconverter.composeapp.generated.resources.NotoSansJP_VariableFont_wght
import jpyconverter.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.WebResourcesConfiguration.resourcePathMapping
import org.jetbrains.compose.resources.configureWebResources
import org.jetbrains.compose.resources.preloadFont

@OptIn(InternalComposeUiApi::class)
actual object LocalAppTheme {
    actual val current: Boolean
        @Composable get() = LocalSystemTheme.current == SystemTheme.Dark

    @Composable
    actual infix fun provides(value: Boolean?): ProvidedValue<*> {
        val new = when(value) {
            true -> SystemTheme.Dark
            false -> SystemTheme.Light
            null -> LocalSystemTheme.current
        }

        return LocalSystemTheme.provides(new)
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    configureWebResources {
        // Overrides the resource location
        resourcePathMapping { path -> "./$path" }
    }
    ComposeViewport {
        val fontFamilyResolver = LocalFontFamilyResolver.current
        var fontsLoaded by remember { mutableStateOf(false) }

        val notoSansJPFont by preloadFont(Res.font.NotoSansJP_VariableFont_wght)

        if (fontsLoaded && notoSansJPFont != null) {
            App()
        }

        LaunchedEffect(notoSansJPFont) {
            if (notoSansJPFont != null) {
                fontFamilyResolver.preload(FontFamily(listOf(notoSansJPFont!!)))
                fontsLoaded = true
            }
        }
    }
}

package org.jpy.converter.util

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js

@OptIn(ExperimentalWasmJsInterop::class)
actual fun formatDouble(value: Double, decimals: Int): String {
    return js("value.toFixed(decimals)") as String
}
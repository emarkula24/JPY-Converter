package org.jpy.converter.util

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js


@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(value, decimals) => value.toFixed(decimals)")
external fun toFixed(value: Double, decimals: Int): String

actual fun formatDouble(value: Double, decimals: Int): String {
    return toFixed(value, decimals)
}

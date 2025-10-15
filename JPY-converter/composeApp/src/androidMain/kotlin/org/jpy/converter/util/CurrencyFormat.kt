package org.jpy.converter.util


actual fun formatDouble(value: Double, decimals: Int): String {
    return "%.${decimals}f".format(value)
}
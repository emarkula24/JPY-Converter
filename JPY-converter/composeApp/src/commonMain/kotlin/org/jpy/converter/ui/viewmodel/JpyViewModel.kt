package org.jpy.converter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.jpy.converter.util.formatDouble

class JpyViewModel : ViewModel() {

    var selectedJpyMultiplier: String by mutableStateOf("")
        private set
    var selectedIntValue: Int by mutableStateOf(0)
        private set
    var multiplierSelectFlag: Boolean by mutableStateOf(false)
        private set
    var formattedJpyOutput: String by mutableStateOf("")
        private set
    fun updateFormattedJpyOutput(input: String) {
        val value = input.toDoubleOrNull() ?: 0.0
        formattedJpyOutput = formatJpyValue(value)
    }
    fun onMultiplierSelected(multiplier: String, intValue: Int) {
        selectedJpyMultiplier = multiplier
        selectedIntValue = intValue
        multiplierSelectFlag = !multiplierSelectFlag
    }

    fun formatJpyValue(value: Double): String {
        var oku = 0
        var man = 0
        var sen = 0
        var remainder: Double = value
        while (remainder > 999) {
            if ( remainder >= 100_000_000) {
                oku += 1
                remainder -= 100_000_000
            }
            if (remainder >= 10_000) {
                man += 1
                remainder -= 10_000
            }
            if (remainder >= 1_000) {
                sen += 1
                remainder -= 1_000
            }
            if (man >= 10_000) {
                oku += 1
                man -= 10_000
            }
            if (sen >= 10) {
                man += 1
                sen -= 10
            }
        }
        val builder = StringBuilder()
        if (oku != 0) { builder.append("$oku 億 ") }
        if (man != 0) { builder.append("$man 万 ") }
        if (sen != 0) { builder.append("$sen 千 ") }
        if (remainder > 0.0) builder.append(formatDouble(remainder, decimals = 0))

        return builder.toString()
    }
}
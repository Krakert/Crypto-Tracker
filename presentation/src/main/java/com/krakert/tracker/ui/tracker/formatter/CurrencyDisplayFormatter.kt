package com.krakert.tracker.ui.tracker.formatter

import javax.inject.Inject

class CurrencyDisplayFormatter @Inject constructor() {
    fun format(currencySymbol: String, value: Any): String {
        val formattedValue = when (value) {
            is Double -> String.format("%8.2f", value)
            is Int -> value.toString() // Format integers as is
            else -> value.toString() // Default case for other types
        }
        return "$currencySymbol $formattedValue"
    }
}
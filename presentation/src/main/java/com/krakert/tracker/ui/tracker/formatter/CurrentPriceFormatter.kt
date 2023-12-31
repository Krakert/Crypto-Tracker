package com.krakert.tracker.ui.tracker.formatter

import java.text.NumberFormat
import javax.inject.Inject

class CurrentPriceFormatter @Inject constructor() {

    fun map(price: Double): String {
        return when {
            price < 1f -> {
                format(price, 5, ",", ".")
            }

            price in 1f..10f -> {
                format(price, 4, ",", ".")
            }

            price in 10f..100f -> {
                format(price, 3, ",", ".")
            }

            price > 100f -> {
                format(price, 2)
            }

            else -> {
                format(price, 5, ",", ".")
            }
        }
    }

    private fun format(
        number: Double,
        maximumFractionDigits: Int,
        oldValue: String? = null,
        newValue: String? = null,
    ): String {
        val formatter = NumberFormat.getInstance()
        formatter.maximumFractionDigits = maximumFractionDigits
        return if (oldValue != null && newValue != null) {
            formatter.format(number).replace(oldValue, newValue)
        } else {
            formatter.format(number)
        }
    }

}

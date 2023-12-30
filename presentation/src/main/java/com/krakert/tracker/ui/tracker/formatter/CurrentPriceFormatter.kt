package com.krakert.tracker.ui.tracker.formatter

import java.text.NumberFormat
import javax.inject.Inject

class CurrentPriceFormatter @Inject constructor() {

    private val formatter = NumberFormat.getInstance()
    fun map(price: Double): String {
        return when {
            price > 1f -> {
                formatter.maximumFractionDigits = 2
                formatter.format(price)
            }

            price < 1f -> {
                formatter.maximumFractionDigits = 5
                formatter.format(price).replace(",", ".")
            }

            else -> {
                formatter.maximumFractionDigits = 5
                formatter.format(price).replace(",", ".")
            }
        }
    }
}

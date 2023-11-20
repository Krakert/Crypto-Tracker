package com.krakert.tracker.ui.tracker.formatter

import javax.inject.Inject

class DoubleDisplayFormatter @Inject constructor() {
    fun format(formatter: String, value: Double): String {
        return String.format(formatter, value)
    }
}
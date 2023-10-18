package com.krakert.tracker.domain.tracker.model

data class Preferences(
    val marketChartAmountDays : Int,
    val currency: String,
    val cacheRateTime: Int,
)

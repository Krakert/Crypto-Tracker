package com.krakert.tracker.models

import com.google.gson.annotations.SerializedName

data class MarketChart(
    @SerializedName("prices")
    val prices: List<List<Double>>,
)
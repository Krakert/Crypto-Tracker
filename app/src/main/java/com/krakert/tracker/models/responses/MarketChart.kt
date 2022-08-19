package com.krakert.tracker.models.responses

import com.google.gson.annotations.SerializedName

data class MarketChart(
    @SerializedName("prices")
    val prices: List<List<Double>>,
)
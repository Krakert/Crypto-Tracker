package com.krakert.tracker.data.tracker.entity

import com.google.gson.annotations.SerializedName

data class MarketChartEntity(
    @SerializedName("prices")
    val prices: List<List<Double?>?>,
)
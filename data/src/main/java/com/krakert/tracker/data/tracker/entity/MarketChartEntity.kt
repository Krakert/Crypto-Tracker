package com.krakert.tracker.data.components.tracker.entity

import com.google.gson.annotations.SerializedName

data class MarketChartEntity(
    @SerializedName("prices")
    val prices: List<List<Double?>?>,
)
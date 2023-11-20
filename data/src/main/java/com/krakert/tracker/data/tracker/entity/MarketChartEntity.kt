package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.Serializable

// @SerialName
@Serializable
data class MarketChartEntity(
   val prices: List<List<Double?>?>,
)
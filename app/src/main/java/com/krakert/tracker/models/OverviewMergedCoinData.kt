package com.krakert.tracker.models

data class OverviewMergedCoinData(
    val id: String,
    val name: String,
    val currentPrice: String,
    val graphData : List<GraphItem>
)

data class GraphItem(
    val timestamp: Int,
    val price: Double
)

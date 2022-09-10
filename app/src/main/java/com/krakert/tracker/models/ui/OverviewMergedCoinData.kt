package com.krakert.tracker.models.ui

data class OverviewMergedCoinData(
    val id: String,
    val name: String,
    val currentPrice: String,
    val graphData: List<GraphItem>,
    val timestamp: Long
)

data class GraphItem(
    val timestamp: Long,
    val price: Double
)

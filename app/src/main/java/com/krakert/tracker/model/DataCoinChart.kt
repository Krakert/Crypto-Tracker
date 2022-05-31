package com.krakert.tracker.model

data class MarketChart(
    val prices: List<List<String>>,
)

data class DataCoin(
    val history: MarketChart,
    val currentPrice: Double,
)
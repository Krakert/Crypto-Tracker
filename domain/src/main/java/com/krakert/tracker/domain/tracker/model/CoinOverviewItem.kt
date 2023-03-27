package com.krakert.tracker.domain.tracker.model

data class CoinOverviewItem(
    val name: String,
    val currentPrice: Double,
    val marketChart: MarketChart
)
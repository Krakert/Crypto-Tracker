package com.krakert.tracker.domain.tracker.model

data class CoinOverviewItem(
    val name: String,
    val id: String,
    val currentPrice: Double,
    val marketChart: MarketChart,
    val currency: String
)
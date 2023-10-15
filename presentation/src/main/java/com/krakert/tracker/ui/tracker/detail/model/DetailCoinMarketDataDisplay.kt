package com.krakert.tracker.ui.tracker.detail.model

data class DetailCoinMarketDataDisplay (
    val currentPrice: Double,
    val marketCap: Double,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val marketCapChangePercentage24h: Double,
    val circulatingSupply: Double,
    val lastUpdated: String,
)
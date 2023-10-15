package com.krakert.tracker.ui.tracker.detail.model

data class DetailCoinMarketDataDisplay(
    val currentPrice: String,
    val marketCap: String,
    val high24h: String,
    val low24h: String,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val marketCapChangePercentage24h: Double,
    val circulatingSupply: String,
    val lastUpdated: String,
)
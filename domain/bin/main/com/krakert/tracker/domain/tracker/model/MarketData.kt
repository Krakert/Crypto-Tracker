package com.krakert.tracker.domain.tracker.model

data class MarketData(
    val currentPrice: Map<String, Double>,
    val marketCap: Map<String, Double>,
    val high24h: Map<String, Double>,
    val low24h: Map<String, Double>,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val circulatingSupply: Double,
    val lastUpdated: String,
)
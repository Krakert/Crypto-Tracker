package com.krakert.tracker.data.tracker.entity

data class MarketDataEntity(
    val currentPrice: Map<String, Double>?,
    val marketCap: Map<String, Double>?,
    val high24h: Map<String, Double>?,
    val low24h: Map<String, Double>?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val priceChangePercentage7d: Double?,
    val marketCapChangePercentage24h: Double?,
    val circulatingSupply: Double?,
)
package com.krakert.tracker.models.ui

import com.krakert.tracker.models.responses.Image

data class DetailsCoin(
    val name: String,
    val image: Image?,
    val currentPrice: String,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val circulatingSupply: Double,
    val high24h: Double,
    val low24h: Double,
    val marketCap: Double,
    val marketCapChangePercentage24h: Double,
)
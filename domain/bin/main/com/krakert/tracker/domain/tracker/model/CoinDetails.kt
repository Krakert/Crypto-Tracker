package com.krakert.tracker.domain.tracker.model

data class CoinDetails (
    val id: String,
    val symbol: String,
    val name: String,
    val images: Images,
    val marketData: MarketData
    )
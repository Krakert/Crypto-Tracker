package com.krakert.tracker.data.tracker.entity

data class CoinCurrentDataEntity(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val images: ImagesEntity?,
    val marketData: MarketDataEntity?,
)
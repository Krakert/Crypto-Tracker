package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinCurrentDataEntity(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: ImagesEntity?,
    @SerialName("market_data") val marketData: MarketDataEntity?,
)
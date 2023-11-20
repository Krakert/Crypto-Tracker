package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketDataEntity(
    @SerialName("current_price") val currentPrice: Map<String, Double>?,
    @SerialName("market_cap") val marketCap: Map<String, Double>?,
    @SerialName("high_24h") val high24h: Map<String, Double>?,
    @SerialName("low_24h") val low24h: Map<String, Double>?,
    @SerialName("price_change_24h_in_currency") val priceChange24hInCurrency: Map<String, Double>?,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double?,
    @SerialName("price_change_percentage_7d") val priceChangePercentage7d: Double?,
    @SerialName("market_cap_change_percentage_24h_in_currency") val marketCapChangePercentage24hInCurrency: Map<String, Double>?,
    @SerialName("circulating_supply") val circulatingSupply: Double?,
    @SerialName("last_updated") val lastUpdated: String?,
)
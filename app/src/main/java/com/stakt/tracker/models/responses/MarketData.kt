package com.stakt.tracker.models.responses

import com.google.gson.annotations.SerializedName


data class MarketData(
    @SerializedName("current_price")
    val currentPrice: Map<String, Double>? = null,
    @SerializedName("market_cap")
    val marketCap: Map<String, Double>? = null,
    @SerializedName("high_24h")
    val high24h: Map<String, Double> = emptyMap(),
    @SerializedName("low_24h")
    val low24h: Map<String, Double> = emptyMap(),
    @SerializedName("price_change_24h")
    val priceChange24h: Double = 0.0,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double = 0.0,
    @SerializedName("price_change_percentage_7d")
    val priceChangePercentage7d: Double = 0.0,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double = 0.0,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double = 0.0,
)
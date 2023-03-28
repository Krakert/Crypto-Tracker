package com.krakert.tracker.data.tracker.entity.database

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DBMarketDataEntity(
    @PrimaryKey val id: String,
    @SerializedName("current_price") val currentPrice: Map<String, Double>,
    @SerializedName("market_cap") val marketCap: Map<String, Double>,
    @SerializedName("high_24h") val high24h: Map<String, Double>,
    @SerializedName("low_24h") val low24h: Map<String, Double>,
    @SerializedName("price_change_24h") val priceChange24h: Double,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double,
    @SerializedName("price_change_percentage_7d") val priceChangePercentage7d: Double,
    @SerializedName("market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double,
    @SerializedName("circulating_supply") val circulatingSupply: Double,
)
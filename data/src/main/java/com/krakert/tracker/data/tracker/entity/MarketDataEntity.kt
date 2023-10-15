package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.Serializable

@Serializable
data class MarketDataEntity(
    val current_price: Map<String, Double>?,
    val market_cap: Map<String, Double>?,
    val high_24h: Map<String, Double>?,
    val low_24h: Map<String, Double>?,
    val price_change_24h_in_currency: Map<String, Double>?,
    val price_change_percentage_24h: Double?,
    val price_change_percentage_7d: Double?,
    val market_cap_change_percentage_24h_in_currency: Map<String, Double>?,
    val circulating_supply: Double?,
    val last_updated: String?,
)
package com.krakert.tracker.data.components.tracker.entity

import com.google.gson.annotations.SerializedName

data class ListCoinsItemEntity(
    val circulating_supply: Double?,
    val current_price: Double?,
    val fully_diluted_valuation: Long?,
    val high_24h: Double?,
    @SerializedName("id") val id: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    val last_updated: String?,
    val low_24h: Double?,
    val market_cap: Long?,
    val market_cap_change_24h: Double?,
    val market_cap_change_percentage_24h: Double?,
    val market_cap_rank: Int?,
    val max_supply: Double?,
    @SerializedName("name") val name: String?,
    val price_change_24h: Double?,
    val price_change_percentage_24h: Double?,
    val symbol: String?,
    val total_supply: Double?,
    val total_volume: Long?,
)
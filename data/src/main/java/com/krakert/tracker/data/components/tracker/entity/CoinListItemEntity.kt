package com.krakert.tracker.data.components.tracker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coinTable")
data class CoinsListItemEntity (
    val circulating_supply: Double?,
    val current_price: Double?,
    val fully_diluted_valuation: Long?,
    val high_24h: Double?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String?,
    @ColumnInfo(name = "url")
    @SerializedName("image")
    val image: String?,
    val last_updated: String?,
    val low_24h: Double?,
    val market_cap: Long?,
    val market_cap_change_24h: Double?,
    val market_cap_change_percentage_24h: Double?,
    val market_cap_rank: Int?,
    val max_supply: Double?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,
    val price_change_24h: Double?,
    val price_change_percentage_24h: Double?,
    val symbol: String?,
    val total_supply: Double?,
    val total_volume: Long?,
)
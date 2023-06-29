package com.krakert.tracker.data.tracker.entity.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "market_data",
    foreignKeys = [
        ForeignKey(
            entity = DBCurrentPriceEntity::class,
            childColumns = ["current_price_id"],
            parentColumns = ["id"]
        ),
        ForeignKey(
            entity = DBMarkerCapEntity::class,
            childColumns = ["market_cap_id"],
            parentColumns = ["id"]
        ),
    ]
)
data class DBMarketDataEntity(
    @ColumnInfo(name = "current_price_id") val current_price: Long?,
    @ColumnInfo(name = "market_cap_id") val market_cap: Long?,
    @ColumnInfo(name = "high_24h_id") val high_24h: Long?,
    @ColumnInfo(name = "low_24h_id") val low_24h: Long?,
    @ColumnInfo(name = "price_change_24h_id") val price_change_24h_in_currency: Long?,
    @ColumnInfo(name = "price_change_percentage_24h") val price_change_percentage_24h: Double?,
    @ColumnInfo(name = "price_change_percentage_7d") val price_change_percentage_7d: Double?,
    @ColumnInfo(name = "market_cap_change_percentage_24h_id") val market_cap_change_percentage_24h_in_currency: Long?,
    @ColumnInfo(name = "circulating_supply") val circulating_supply: Double?,
    @ColumnInfo(name = "last_updated") val last_updated: String?,
)
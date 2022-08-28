package com.krakert.tracker.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Wrapper class, is used to save the MAP from the [CoinGeckoApiService.getPriceByListCoinIds] call
 * so it can be stored in the DB.
 */

@Entity(tableName = "priceCoinsTable")
data class PriceCoins(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "data")
    val data: Map<String, MutableMap<String, Any>>
)

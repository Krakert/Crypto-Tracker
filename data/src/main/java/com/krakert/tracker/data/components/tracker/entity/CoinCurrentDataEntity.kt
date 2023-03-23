package com.krakert.tracker.data.components.tracker.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "detailsCoinTable")
data class CoinCurrentDataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String?,

    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    val symbol: String?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: ImagesEntity?,

    @ColumnInfo(name = "market_data")
    @SerializedName("market_data")
    val marketData: MarketDataEntity? = null,
)
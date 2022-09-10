package com.krakert.tracker.models.responses

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.krakert.tracker.models.Links


@Entity(tableName = "detailsCoinTable")
data class CoinFullData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    val symbol: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: Image,

    @ColumnInfo(name = "market_data")
    @SerializedName("market_data")
    val marketData: MarketData? = null,
)
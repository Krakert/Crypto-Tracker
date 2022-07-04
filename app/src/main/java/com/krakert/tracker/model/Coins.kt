package com.krakert.tracker.model

import android.os.Parcelable
import androidx.compose.ui.text.toLowerCase
import androidx.room.*
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

data class Coins(
    @SerializedName("data")
    val coins: List<Coin>,
    @SerializedName("timestamp")
    val timestamp: Long
)

@Parcelize
@Entity(tableName = "coinTable")
data class Coin(
    @SerializedName("changePercent24Hr")
    val changePercent24Hr: String? = null,

    @SerializedName("explorer")
    val explorer: String? = null,

    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @SerializedName("marketCapUsd")
    val marketCapUsd: String? = null,

    @SerializedName("maxSupply")
    val maxSupply: String? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("priceUsd")
    val priceUsd: String? = null,

    @SerializedName("rank")
    val rank: String? = null,

    @SerializedName("supply")
    val supply: String? = null,

    @SerializedName("symbol")
    val symbol: String? = null,

    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: String? = null,

    @SerializedName("vwap24Hr")
    val vwap24Hr: String? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long

) : Parcelable {
    fun getIcon() = "https://assets.coincap.io/assets/icons/${symbol?.lowercase()}@2x.png"
}

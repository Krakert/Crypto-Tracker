package com.krakert.tracker.models.responses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * This class is used to store all the data for each coin in the "add coin" screen of the app
 */
class ListCoins : ArrayList<Coin>()

@Entity(tableName = "coinTable")
data class Coin(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "url")
    @SerializedName("image")
    val image: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "Favorite")
    var isFavorite: Boolean = false
)
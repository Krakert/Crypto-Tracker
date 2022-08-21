package com.krakert.tracker.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * This class is used to store all the data for each coin in the "add coin" screen of the app
 */
class ListCoins : ArrayList<Coin>()

@Parcelize
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

    @ColumnInfo(name = "timeStamp")
    var timeStamp: Long = 0
) : Parcelable {
    fun getIcon() = image
}
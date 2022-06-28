package com.krakert.tracker.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.util.*

data class Coins (
    val Coins: List<Coin>? = null,
)

@Parcelize
@Entity(tableName = "coinTable")
data class Coin (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "idCoin")
    val idCoin: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "url")
    val symbol: String = "",
    @ColumnInfo(name = "timeStamp")
    var timeStamp: Long = 0
) : Parcelable

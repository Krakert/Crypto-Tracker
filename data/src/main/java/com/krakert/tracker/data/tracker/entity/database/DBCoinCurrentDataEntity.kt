package com.krakert.tracker.data.tracker.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "detailsCoinTable", foreignKeys = [ForeignKey(
        entity = DBImagesEntity::class,
        parentColumns = arrayOf("image"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = DBMarketDataEntity::class,
        parentColumns = arrayOf("marketData"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DBCoinCurrentDataEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") @SerializedName("id") val id: String?,
    @ColumnInfo(name = "symbol") @SerializedName("symbol") val symbol: String?,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String?,
    @ColumnInfo(name = "image") @SerializedName("images") val images: DBImagesEntity?,
    @ColumnInfo(name = "market_data") @SerializedName("market_data") val marketData: DBMarketDataEntity?,
)

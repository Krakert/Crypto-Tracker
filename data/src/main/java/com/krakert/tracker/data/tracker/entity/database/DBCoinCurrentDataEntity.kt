package com.krakert.tracker.data.tracker.entity.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coinCurrentData")
data class DBCoinCurrentDataEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "symbol") val symbol: String?,
    @ColumnInfo(name = "name") val name: String?,
    @Embedded val images: DBImagesEntity,
//    @Embedded val marketData: DBMarketDataEntity
)

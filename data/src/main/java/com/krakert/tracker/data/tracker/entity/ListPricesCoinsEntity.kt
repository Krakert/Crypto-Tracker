package com.krakert.tracker.data.tracker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "priceCoinsTable")
data class ListPricesCoinsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "data")
    val data: Map<String, MutableMap<String, Any>>
)
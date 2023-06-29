package com.krakert.tracker.data.tracker.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_cap")
data class DBMarkerCapEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "value") val value: Double
)

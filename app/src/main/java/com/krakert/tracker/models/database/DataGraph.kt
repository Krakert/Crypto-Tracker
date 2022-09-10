package com.krakert.tracker.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krakert.tracker.models.responses.MarketChart

/**
 * Wrapper class for the data class [MarketChart] so it can be stored in the DB.
 */

@Entity(tableName = "marketChartTable")
data class DataGraph (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "data")
    val data : MarketChart
)
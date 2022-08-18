package com.krakert.tracker.models

import androidx.room.*
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dataCoinTable")
data class DataCoinChart(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    val history: List<String>,

    @ColumnInfo(name = "currentPrice")
    val currentPrice: Double,

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    val timestamp: Long
)

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
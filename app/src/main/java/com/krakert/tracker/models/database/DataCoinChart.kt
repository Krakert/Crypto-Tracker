package com.krakert.tracker.models.database

import androidx.room.*
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonElement
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

    @TypeConverter
    fun stringToMap(value: JsonElement): MutableMap<String, String> {
        return Gson().fromJson(value,  object : TypeToken<Map<String, String>>() {}.type)
    }

    @TypeConverter
    fun mapToString(value: MutableMap<String, String>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}
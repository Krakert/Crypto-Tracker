package com.krakert.tracker.model

import androidx.room.*
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type

@Entity(tableName = "dataCoinTable")
data class DataCoin(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "history")
    val history: List<List<String>>,
    @ColumnInfo(name = "currentPrice")
    val currentPrice: Double,
    @ColumnInfo(name = "timeStamp")
    var timeStamp: Long
)

class Converters {
    @TypeConverter
    fun listToJson(value: List<List<String>>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<List<String>> {
        val listType: Type = object : TypeToken<ArrayList<ArrayList<String>>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
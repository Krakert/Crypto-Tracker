package com.krakert.tracker.data.components.storage.mapper

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.krakert.tracker.data.tracker.entity.ImagesEntity
import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.data.tracker.entity.MarketDataEntity

@ProvidedTypeConverter
class DataConverters {
    /*---------------- Used for the CoinCurrentDataEntity ----------------*/
    @TypeConverter
    fun imageToJson(image: ImagesEntity): String {
        return Gson().toJson(image)
    }

    @TypeConverter
    fun jsonToImage(string: String): ImagesEntity {
        return Gson().fromJson(string, ImagesEntity::class.java)
    }

    @TypeConverter
    fun marketDataToJson(marketData: MarketDataEntity): String {
        return Gson().toJson(marketData)
    }

    @TypeConverter
    fun jsonToMarketData(string: String): MarketDataEntity {
        return Gson().fromJson(string, MarketDataEntity::class.java)
    }
    /*-----------------------------------------------------------*/

    /*----------------- Used for the PriceCoins -----------------*/
    @TypeConverter
    fun mapToJson(map: Map<String, MutableMap<String, Any>>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun jsonToPriceCoins(json: String): Map<String, MutableMap<String, Any>> {
        return Gson().fromJson(json, object : TypeToken<Map<String, MutableMap<String, Any>>>() {}.type)
    }
    /*-----------------------------------------------------------*/

    /*----------------- Used for the PriceCoins -----------------*/
    @TypeConverter
    fun marketChartToJson(marketChart: MarketChartEntity): String {
        return Gson().toJson(marketChart)
    }

    @TypeConverter
    fun jsonToMarketChart(json: String): MarketChartEntity {
        return Gson().fromJson(json, MarketChartEntity::class.java)
    }
    /*-----------------------------------------------------------*/

}
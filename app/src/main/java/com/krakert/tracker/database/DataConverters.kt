package com.krakert.tracker.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.models.database.PriceCoins
import com.krakert.tracker.models.responses.Image
import com.krakert.tracker.models.responses.MarketChart
import com.krakert.tracker.models.responses.MarketData

@ProvidedTypeConverter
class DataConverters {
    /*---------------- Used for the CoinFullData ----------------*/
    @TypeConverter
    fun imageToJson(image: Image): String {
        return Gson().toJson(image)
    }

    @TypeConverter
    fun jsonToImage(string: String): Image {
        return Gson().fromJson(string, Image::class.java)
    }

    @TypeConverter
    fun marketDataToJson(marketData: MarketData): String {
        return Gson().toJson(marketData)
    }

    @TypeConverter
    fun jsonToMarketData(string: String): MarketData {
        return Gson().fromJson(string, MarketData::class.java)
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
    fun marketChartToJson(marketChart: MarketChart): String {
        return Gson().toJson(marketChart)
    }

    @TypeConverter
    fun jsonToMarketChart(json: String): MarketChart {
        return Gson().fromJson(json, MarketChart::class.java)
    }
    /*-----------------------------------------------------------*/

}
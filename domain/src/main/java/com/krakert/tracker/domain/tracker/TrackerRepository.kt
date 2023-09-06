package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import java.util.Currency


interface TrackerRepository {
    suspend fun getListCoins(): Result<ListCoins>

//    suspend fun getDetailsCoin(
//        coinId: String,
//    ): Result<CoinDetails>

//    suspend fun getOverview(): Result<CoinOverview>

//    suspend fun getHistoryByCoinId(
//        coinId: String,
//        currency: String,
//        days: String,
//    ): Result<MarketChart>

//    suspend fun getPriceCoins(
//        idCoins: String,
//        currency: String):
//        Result<Map<String, MutableMap<String, Any>>>

//    fun getCoinsIdString() : String
//
//    fun isOnline(): Boolean

}

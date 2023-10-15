package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoins


interface TrackerRepository {
    suspend fun getListCoins(): Result<ListCoins>

    suspend fun getOverview(): Result<CoinOverview>

    suspend fun addFavouriteCoin(id: String, name: String): Result<Boolean>

    suspend fun removeFavouriteCoin(id: String, name: String): Result<Boolean>

    suspend fun getFavouriteCoins(): Result<ListFavouriteCoins>

    suspend fun getDetailsCoin(coinId: String): Result<CoinDetails>


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

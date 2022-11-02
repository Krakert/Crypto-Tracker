package com.krakert.tracker.repository

import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.responses.ListCoins
import com.krakert.tracker.models.responses.CoinFullData
import com.krakert.tracker.models.responses.MarketChart
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    //TODO: for now duplicate of what the implementing class has - generify
    suspend fun getListCoins(
        currency: String = "usd",
        ids: String? = null,
        order: String = "market_cap_desc",
        perPage: Int = 100,
        page: Int = 1,
    ): Flow<Resource<ListCoins>>

    suspend fun getDetailsCoinByCoinId(
        coinId: String,
    ): Flow<Resource<CoinFullData>>

    suspend fun getHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String,
    ): Flow<Resource<MarketChart>>

    suspend fun getPriceCoins(
        idCoins: String, 
        currency: String):
            Flow<Resource<Map<String, MutableMap<String, Any>>>>

    fun isOnline(): Boolean
}

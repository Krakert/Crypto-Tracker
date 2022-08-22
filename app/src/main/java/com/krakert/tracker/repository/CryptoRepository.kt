package com.krakert.tracker.repository

import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.ListCoins
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
    ): Resource<ListCoins>

    suspend fun getDetailsCoinByCoinId(
        coinId: String,
        localization: String = "false",
        tickers: Boolean = false,
        markerData: Boolean = true,
        communityData: Boolean = false,
        developerData: Boolean = false,
        sparkline: Boolean = false,
    ): Resource<CoinFullData>

    suspend fun getHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String,
    ): Resource<MarketChart>

    fun getPriceCoins(idCoins: String, currency: String):
            Flow<Resource<MutableMap<String, MutableMap<String, Any>>>>

    fun shouldFetch() : Boolean
}

package com.krakert.tracker.api

import com.krakert.tracker.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApiService {
    @GET("coins/markets")
    suspend fun getListCoins(
        @Query("vs_currency") currency: String,
        @Query("ids") ids: String?,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ) : ListCoins

    @GET("simple/price")
    suspend fun getPriceByListCoinIds(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String,
        @Query("include_market_cap") marketCap: String,
        @Query("include_24hr_vol") dayVol: String,
        @Query("include_24hr_change") dayChange: String,
        @Query("include_last_updated_at") lastUpdated: String
    ) : MutableMap<String, MutableMap<String, Any>>

    @GET("coins/{id}")
    suspend fun getDetailsCoinByCoinId(
        @Path("id") id: String,
        @Query("localization") localization: String,
        @Query("tickers") tickers: Boolean,
        @Query("market_data") markerData: Boolean,
        @Query("community_data") communityData: Boolean,
        @Query("developer_data") developerData: Boolean,
        @Query("sparkline") sparkline: Boolean
    )  : CoinFullData

    @GET("coins/{id}/market_chart")
    suspend fun getHistoryByCoinId(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String
    ) : MarketChart
}
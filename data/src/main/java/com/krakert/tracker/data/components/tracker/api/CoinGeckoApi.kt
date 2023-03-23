package com.krakert.tracker.data.components.tracker.api

import com.krakert.tracker.data.components.tracker.entity.CoinCurrentDataEntity
import com.krakert.tracker.data.components.tracker.entity.CoinsListEntity
import com.krakert.tracker.data.components.tracker.entity.MarketChartEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/markets")
    suspend fun getListCoins(
        @Query("vs_currency") currency: String,
        @Query("ids") ids: String?,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ) : Response<CoinsListEntity>

    @GET("simple/price")
    suspend fun getPriceByListCoinIds(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String,
        @Query("include_market_cap") marketCap: String,
        @Query("include_24hr_vol") dayVol: String,
        @Query("include_24hr_change") dayChange: String,
        @Query("include_last_updated_at") lastUpdated: String
    ) : Response<Map<String, MutableMap<String, Any>>>

    @GET("coins/{id}")
    suspend fun getDetailsCoinByCoinId(
        @Path("id") coinId: String,
        @Query("localization") localization: String,
        @Query("tickers") tickers: Boolean,
        @Query("market_data") markerData: Boolean,
        @Query("community_data") communityData: Boolean,
        @Query("developer_data") developerData: Boolean,
        @Query("sparkline") sparkline: Boolean
    )  : Response<CoinCurrentDataEntity>

    @GET("coins/{id}/market_chart")
    suspend fun getHistoryByCoinId(
        @Path("id") coinId: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String
    ) : Response<MarketChartEntity>
}
package com.krakert.tracker.data.tracker.api

import com.krakert.tracker.data.components.tracker.entity.CoinCurrentDataEntity
import com.krakert.tracker.data.components.tracker.entity.ListCoinsEntity
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
    ) : Response<ListCoinsEntity>

    @GET("simple/price")
    suspend fun getPriceByListCoinIds(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String,
        @Query("include_market_cap") marketCap: String = "false",
        @Query("include_24hr_vol") dayVol: String = "false",
        @Query("include_24hr_change") dayChange: String = "false",
        @Query("include_last_updated_at") lastUpdated: String = "false"
    ) : Response<Map<String, MutableMap<String, Any>>>

    @GET("coins/{id}")
    suspend fun getDetailsCoin(
        @Path("id") coinId: String,
        @Query("localization") localization: String = "false",
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") markerData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
    )  : Response<CoinCurrentDataEntity>

    @GET("coins/{id}/market_chart")
    suspend fun getHistoryByCoinId(
        @Path("id") coinId: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String
    ) : Response<MarketChartEntity>
}
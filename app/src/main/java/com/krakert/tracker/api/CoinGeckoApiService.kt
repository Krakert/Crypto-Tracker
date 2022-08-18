package com.krakert.tracker.api

import com.krakert.tracker.models.*
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApiService {
    @GET("coins/markets")
    suspend fun getListCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("ids") ids: String? = null,
        @Query("order") order: String? = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ) : ListCoins

    @GET("simple/price")
    suspend fun getPriceByListCoinIds(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String = "usd",
        @Query("include_market_cap") markerCap: String = "false",
        @Query("include_24hr_vol") dayVol: String = "false",
        @Query("include_24hr_change") dayChange: String = "true",
        @Query("include_last_updated_at") lastUpdated: String = "false"
    ) : HashMap<String, CoinPriceData>

    @GET("coins/{id}")
    suspend fun getDetailsCoinByCoinId(
        @Path("id") id: String,
        @Query("localization") localization: String = "false",
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") markerData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
    )  : CoinFullData
}
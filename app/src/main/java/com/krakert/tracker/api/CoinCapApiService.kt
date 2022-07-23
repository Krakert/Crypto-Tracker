package com.krakert.tracker.api

import com.krakert.tracker.model.Coins
import com.krakert.tracker.model.DataCoinChart
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinCapApiService {
    @GET("/v2/assets")
    suspend fun getListCoins(
    @Query("limit") limit: Int = 50,
    @Query("offset") offset: Int = 0
    ) : Coins

    @GET("/v2/assets/{id}")
    suspend fun getDetailsByCoinId(
        @Path("id") id: String
    )

    @GET("/v2/assets/{id}/history")
    suspend fun getHistoryByCoinId(
        @Path("id") id: String,
        @Query("interval") interval: String = "m1",
        @Query("start") start: Long = (System.currentTimeMillis() - 60 * 60 * 1000),
        @Query("end") end: Long = System.currentTimeMillis()
    ) : DataCoinChart
}
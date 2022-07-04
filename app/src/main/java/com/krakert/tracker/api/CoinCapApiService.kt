package com.krakert.tracker.api

import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.Coins
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinCapApiService {
    @GET("/v2/assets")
    suspend fun getListCoins(
    @Query("limit") limit: Int = 50,
    @Query("offset") offset: Int = 0
    ) : Coins

    @GET("/v2//assets/{id}")
    suspend fun getDetailsCoin(
        @Path("id") id: String
    )

    @GET("/v2//assets/{id}/history")
    suspend fun getHistoryCoin(
        @Path("id") id: String
    )
}
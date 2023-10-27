package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins

interface ApiRepository {
    suspend fun fetchListCoins(): Result<ListCoins>

    suspend fun fetchOverview(): Result<CoinOverview>

    suspend fun fetchDetailsCoin(coinId: String): Result<CoinDetails>
}
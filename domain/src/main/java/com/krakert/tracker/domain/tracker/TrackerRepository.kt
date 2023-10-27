package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins

interface TrackerRepository {
    suspend fun getListCoins(): Result<ListCoins>

    suspend fun getOverview(): Result<CoinOverview>

    suspend fun getDetailsCoin(coinId: String): Result<CoinDetails>

}

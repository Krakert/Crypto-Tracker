package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import kotlinx.coroutines.flow.Flow

interface TrackerRepository {
    suspend fun getListCoins(): Flow<Result<ListCoins>>

    suspend fun getOverview(): Flow<Result<CoinOverview>>

    suspend fun getDetailsCoin(coinId: String): Flow<Result<CoinDetails>>

}

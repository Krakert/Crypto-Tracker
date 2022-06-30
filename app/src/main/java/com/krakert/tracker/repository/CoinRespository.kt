package com.krakert.tracker.repository

import com.krakert.tracker.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRespository {

    suspend fun getCoinsList() : Flow<List<Coin>>
    fun shouldFetch() : Boolean
}
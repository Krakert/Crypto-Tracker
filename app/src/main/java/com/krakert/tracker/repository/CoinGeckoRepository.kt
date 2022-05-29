package com.krakert.tracker.repository

import com.krakert.tracker.model.MarketChart
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

class CoinGeckoRepository {

    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    suspend fun getHistoryCoin(coinId: String): Flow<MarketChart> {
        return try {
            withTimeout(10_000) {
                val result = MarketChart(
                    prices = coinGecko.getCoinMarketChartById(coinId, "eur", 1.0).prices
                )
                flow{
                    emit(result)
                }
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of data of the coin was unsuccessful")
        }
    }

    inner class CoinGeckoExceptionError(message: String) : Exception(message)
}
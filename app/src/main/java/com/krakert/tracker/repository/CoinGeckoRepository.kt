package com.krakert.tracker.repository

import com.krakert.tracker.model.MarketChart
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.*

const val Currency = "eur"

class CoinGeckoRepository {

    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    suspend fun getHistoryCoin(coinId: String): MarketChart {
        return try {
            withTimeout(10_000) {
                MarketChart(
                    prices = coinGecko.getCoinMarketChartById(coinId, Currency, 7.0).prices
                )
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of data of the coin was unsuccessful")
        }
    }

    suspend fun getLatestPrice(coinId: String): Double {
        return try {
            withTimeout(10_000) {
                coinGecko.getPrice(coinId, Currency)[coinId]!!.getPrice(Currency)
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of data of the coin was unsuccessful")

        }
    }

    inner class CoinGeckoExceptionError(message: String) : Exception(message)
}
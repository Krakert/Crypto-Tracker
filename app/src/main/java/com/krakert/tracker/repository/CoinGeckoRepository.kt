package com.krakert.tracker.repository

import android.content.Context
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.model.Currency
import com.krakert.tracker.model.MarketChart
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.withTimeout
import java.util.*


class CoinGeckoRepository(context: Context) {

    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()

    private val sharedPreference = SharedPreference.sharedPreference(context = context)
    private val daysOfTracking = sharedPreference.AmountDaysTracking.toDouble()
    private val currencyObject = sharedPreference.Currency?.let { Currency.valueOf(it) }
    private val currencyString = currencyObject.toString().lowercase(Locale.getDefault())

    suspend fun getHistoryCoin(coinId: String): MarketChart {
        return try {
            withTimeout(10_000) {
                println(currencyObject?.nameFull)
                MarketChart(
                    prices = coinGecko.getCoinMarketChartById(coinId,
                        currencyString,
                        daysOfTracking
                    ).prices
                )
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of history data of the coin was unsuccessful")
        }
    }

    suspend fun getLatestPrice(coinId: String): Double {
        return try {
            withTimeout(10_000) {
                coinGecko.getPrice(coinId, currencyString)[coinId]!!.getPrice(currencyString)
            }
        } catch (e: Exception) {
            throw CoinGeckoExceptionError("Retrieval of Latest price of the coin was unsuccessful")

        }
    }

    inner class CoinGeckoExceptionError(message: String) : Exception(message)
}